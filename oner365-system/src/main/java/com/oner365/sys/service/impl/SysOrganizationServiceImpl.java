package com.oner365.sys.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.cache.annotation.RedisCachePut;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.ExistsEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.query.Criteria;
import com.oner365.common.query.Restrictions;
import com.oner365.datasource.constants.DataSourceConstants;
import com.oner365.datasource.util.DataSourceUtil;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dao.ISysOrganizationDao;
import com.oner365.sys.entity.DataSourceConfig;
import com.oner365.sys.entity.SysOrganization;
import com.oner365.sys.entity.TreeSelect;
import com.oner365.sys.mapper.SysOrganizationMapper;
import com.oner365.sys.service.ISysOrganizationService;
import com.oner365.util.DataUtils;

/**
 * ISysOrganizationService实现类
 * @author zhaoyong
 */
@Service
public class SysOrganizationServiceImpl implements ISysOrganizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysOrganizationServiceImpl.class);

    private static final String CACHE_NAME = "SysOrganization";

    @Autowired
    private ISysOrganizationDao dao;

    @Autowired
    private SysOrganizationMapper organizationMapper;

    @Override
    @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    public SysOrganization getById(String id) {
        try {
            Optional<SysOrganization> optional = dao.findById(id);
            return optional.orElse(null);
        } catch (Exception e) {
            LOGGER.error("Error getById: ", e);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public int deleteById(String id) {
        dao.deleteById(id);
        return ResultEnum.SUCCESS.getCode();
    }

    @Override
    public long checkCode(String orgId, String code, String type) {
        try {
            Criteria<SysOrganization> criteria = new Criteria<>();
            criteria.add(Restrictions.eq(type, code));
            if (!DataUtils.isEmpty(orgId)) {
                criteria.add(Restrictions.ne(SysConstants.ID, orgId));
            }
            return dao.count(criteria);
        } catch (Exception e) {
            LOGGER.error("Error checkCode:", e);
        }
        return ExistsEnum.NO.getCode();
    }

    @Override
    public boolean checkConnection(String id) {
        try {
            SysOrganization org = getById(id);
            DataSourceConfig config = org.getDataSourceConfig();
            if (config != null) {
                return DataSourceUtil.isConnection(config.getDriverName(), config.getUrl(),
                        config.getUserName(), config.getPassword());
            }
        } catch (Exception e) {
            LOGGER.error("Error checkConnection:", e);
        }
        return false;
    }

    @Override
    public boolean isConnection(String dsType, String ip, int port, String dbName, String userName, String pwd) {
        String driverName;
        String url;
        if (DataSourceConstants.DB_TYPE_MYSQL.equals(dsType)) {
            driverName = DataSourceConstants.DRIVER_NAME_MYSQL;
            url = "jdbc:mysql://" + ip + ":" + port + PublicConstants.DELIMITER + dbName;
        } else {
            driverName = DataSourceConstants.DRIVER_NAME_ORACLE;
            url = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + dbName;
        }
        return DataSourceUtil.isConnection(driverName, url, userName, pwd);
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public SysOrganization save(SysOrganization org) {
        if (DataUtils.isEmpty(org.getId())) {
            org.setId(org.getOrgCode());
            org.setStatus(StatusEnum.YES.getCode());
            org.setCreateTime(LocalDateTime.now());
        }

        org.setUpdateTime(LocalDateTime.now());

        DataSourceConfig dataSourceConfig = org.getDataSourceConfig();
        if (dataSourceConfig != null) {
            String driverName = null;
            String url = null;
            if (DataSourceConstants.DB_TYPE_MYSQL.equals(dataSourceConfig.getDbType())) {
                driverName = DataSourceConstants.DRIVER_NAME_MYSQL;
                url = "jdbc:mysql://" + dataSourceConfig.getIp() + ":" + dataSourceConfig.getPort() + PublicConstants.DELIMITER
                        + dataSourceConfig.getDbName();
            } else if (DataSourceConstants.DB_TYPE_ORACLE.equals(dataSourceConfig.getDbType())) {
                driverName = DataSourceConstants.DRIVER_NAME_ORACLE;
                url = "jdbc:oracle:thin:@" + dataSourceConfig.getIp() + ":" + dataSourceConfig.getPort() + ":"
                        + dataSourceConfig.getDbName();
            }
            dataSourceConfig.setDsType(DataSourceConstants.DS_TYPE_DB);
            dataSourceConfig.setDriverName(driverName);
            dataSourceConfig.setUrl(url);
            dataSourceConfig.setCreateTime(LocalDateTime.now());
            dataSourceConfig.setUpdateTime(LocalDateTime.now());
            org.setDataSourceConfig(dataSourceConfig);
        }
        org = dao.save(org);
        return org;
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public List<SysOrganization> findListByParentId(String parentId) {
        Criteria<SysOrganization> criteria = new Criteria<>();
        criteria.add(Restrictions.eq("parentId", parentId));
        return dao.findAll(criteria);
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public SysOrganization getByCode(String orgCode) {
        return dao.getByCode(orgCode);
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public List<TreeSelect> buildTreeSelect(List<SysOrganization> orgList) {
        List<SysOrganization> menuTrees = buildTree(orgList);
        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public List<SysOrganization> buildTree(List<SysOrganization> orgList) {
        List<SysOrganization> returnList = new ArrayList<>();
        for (SysOrganization t : orgList) {
            // 根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (SysConstants.DEFAULT_PARENT_ID.equals(t.getParentId())) {
                recursionFn(orgList, t);
                returnList.add(t);
            }
        }
        if (returnList.isEmpty()) {
            returnList = orgList;
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list 列表
     * @param t    对象
     */
    private void recursionFn(List<SysOrganization> list, SysOrganization t) {
        // 得到子节点列表
        List<SysOrganization> childList = getChildList(list, t);
        if (!childList.isEmpty()) {
            t.setChildren(childList);
            for (SysOrganization tChild : childList) {
                if (hasChild(list, tChild)) {
                    // 判断是否有子节点
                    for (SysOrganization n : childList) {
                        recursionFn(list, n);
                    }
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysOrganization> getChildList(List<SysOrganization> list, SysOrganization t) {
        List<SysOrganization> tlist = new ArrayList<>();
        for (SysOrganization n : list) {
            if (n.getParentId().equals(t.getId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysOrganization> list, SysOrganization t) {
        return !getChildList(list, t).isEmpty();
    }

    @Override
    public List<SysOrganization> selectList(SysOrganization sysOrg) {
        return organizationMapper.selectList(sysOrg);
    }

    @Override
    public List<String> selectListByUserId(String userId) {
        return organizationMapper.selectListByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public Integer changeStatus(String id, String status) {
        SysOrganization entity = this.getById(id);
        entity.setStatus(status);
        this.save(entity);
        return ResultEnum.SUCCESS.getCode();
    }

}
