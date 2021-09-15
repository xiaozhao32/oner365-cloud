package com.oner365.gateway.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oner365.gateway.constants.GatewayConstants;
import com.oner365.gateway.dao.IGatewayRouteDao;
import com.oner365.gateway.entity.GatewayFilter;
import com.oner365.gateway.entity.GatewayPredicate;
import com.oner365.gateway.entity.GatewayRoute;
import com.oner365.gateway.query.QueryCriteriaBean;
import com.oner365.gateway.query.QueryUtils;
import com.oner365.gateway.rabbitmq.ISyncRouteMqService;
import com.oner365.gateway.service.DynamicRouteService;

import reactor.core.publisher.Mono;

/**
 * DynamicRouteService实现类
 * 
 * @author zhaoyong
 */
@Service
public class DynamicRouteServiceImpl implements DynamicRouteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicRouteServiceImpl.class);

    private static final String HTTP = "http";

    @Autowired
    private IGatewayRouteDao gatewayRouteDao;

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    @Autowired
    private ISyncRouteMqService syncRouteMqService;

    private ApplicationEventPublisher publisher;

    protected static Map<String, String> predicateMap = Maps.newHashMap();

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @Override
    public List<GatewayRoute> findList() {
        List<GatewayRoute> list = gatewayRouteDao.findAll();
        list.forEach(gatewayRoute -> publishEvent(assembleRouteDefinition(gatewayRoute)));
        return list;
    }

    @Override
    public Page<GatewayRoute> pageList(QueryCriteriaBean data) {
        try {
            Pageable pageable = QueryUtils.buildPageRequest(data);
            return gatewayRouteDao.findAll(QueryUtils.buildCriteria(data), pageable);
        } catch (Exception e) {
            LOGGER.error("Error pageList: ", e);
        }
        return null;
    }

    @Override
    public String save(GatewayRoute gatewayRoute) {

        // Filter
        List<GatewayFilter> filters = Lists.newArrayList();
        GatewayFilter gatewayFilter = new GatewayFilter();
        gatewayFilter.setName("StripPrefix");
        Map<String, String> argsFilter = Maps.newHashMap();
        argsFilter.put("parts", "1");
        gatewayFilter.setArgs(argsFilter);
        filters.add(gatewayFilter);
        gatewayRoute.setFilters(filters);

        // Predicates
        List<GatewayPredicate> predicates = Lists.newArrayList();
        GatewayPredicate gatewayPredicate = new GatewayPredicate();
        gatewayPredicate.setName("Path");
        Map<String, String> args = Maps.newHashMap();
        args.put("pattern", gatewayRoute.getPattern());
        gatewayPredicate.setArgs(args);
        predicates.add(gatewayPredicate);
        gatewayRoute.setPredicates(predicates);

        // 页面保存信息
        gatewayRouteDao.save(gatewayRoute);
        publishEvent(assembleRouteDefinition(gatewayRoute));
        syncRouteMqService.syncRoute();
        return "success";
    }

    @Override
    public String update(GatewayRoute gatewayRoute) {
        try {
            delete(gatewayRoute.getId());
            return save(gatewayRoute);
        } catch (Exception e) {
            LOGGER.error("update error:", e);
        }
        return null;
    }

    @Override
    public void delete(String id) {
        try {
            routeDefinitionWriter.delete(Mono.just(id)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            gatewayRouteDao.deleteById(id);
            syncRouteMqService.syncRoute();
        } catch (Exception e) {
            LOGGER.error("delete error:", e);
        }
    }

    @Override
    public List<GatewayRoute> refreshRoute() {
        List<GatewayRoute> list = findList();
        list.forEach(route -> mapRoute(route));
        return list;
    }

    @Override
    public String updateRouteStatus(String id, String status) {
        GatewayRoute gatewayRoute = getById(id);
        if (gatewayRoute != null) {
            gatewayRoute.setStatus(status);
            return update(gatewayRoute);
        }
        return null;
    }

    @Override
    public GatewayRoute getById(String id) {
        Optional<GatewayRoute> optional = gatewayRouteDao.findById(id);
        if (optional.isPresent()) {
            GatewayRoute entity = optional.get();
            if (!entity.getPredicates().isEmpty()) {
                String pattern = entity.getPredicates().get(0).getArgs().get("pattern");
                entity.setPattern(pattern);
            }
        }
        return optional.orElse(null);
    }

    @Override
    public Map<String, String> mapRoute(GatewayRoute route) {
        route.getPredicates().stream().filter(predicate -> predicate.getName().equals(GatewayConstants.PREDICATE_NAME))
                .forEach(predicates -> {
                    String pattern = StringUtils.substring(
                            predicates.getArgs().get(GatewayConstants.PREDICATE_ARGS_PATTERN), 0,
                            predicates.getArgs().get(GatewayConstants.PREDICATE_ARGS_PATTERN).length() - 2);
                    predicateMap.put(pattern,
                            Strings.isNullOrEmpty(route.getStatus()) ? GatewayConstants.ROUT_STATUS_DISABLE
                                    : route.getStatus());
                });
        return predicateMap;
    }

    public void publishEvent(RouteDefinition definition) {
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    /**
     * 把传递进来的参数转换成路由对象
     *
     * @param gatewayRoute 路由对象
     * @return RouteDefinition
     */
    @Override
    public RouteDefinition assembleRouteDefinition(GatewayRoute gatewayRoute) {
        RouteDefinition definition = new RouteDefinition();
        definition.setId(gatewayRoute.getId());
        definition.setOrder(gatewayRoute.getRouteOrder());

        // 设置断言
        List<PredicateDefinition> pdList = new ArrayList<>();
        gatewayRoute.getPredicates().forEach(gpDefinition -> {
            PredicateDefinition predicate = new PredicateDefinition();
            predicate.setArgs(gpDefinition.getArgs());
            predicate.setName(gpDefinition.getName());
            pdList.add(predicate);
        });
        definition.setPredicates(pdList);

        // 设置过滤器
        List<FilterDefinition> filters = new ArrayList<>();
        gatewayRoute.getFilters().forEach(filterDefinition -> {
            FilterDefinition filter = new FilterDefinition();
            filter.setName(filterDefinition.getName());
            filter.setArgs(filterDefinition.getArgs());
            filters.add(filter);
        });
        definition.setFilters(filters);

        URI uri;
        if (gatewayRoute.getUri().startsWith(HTTP)) {
            uri = UriComponentsBuilder.fromHttpUrl(gatewayRoute.getUri()).build().toUri();
        } else {
            // uri为 lb://consumer-service 时使用下面的方法
            uri = URI.create(gatewayRoute.getUri());
        }
        definition.setUri(uri);
        return definition;
    }

}
