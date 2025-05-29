package com.oner365.gateway.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.commons.enums.ResultEnum;
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.gateway.dto.GatewayRouteDto;
import com.oner365.gateway.service.DynamicRouteService;
import com.oner365.gateway.vo.GatewayRouteVo;

/**
 * 动态路由控制
 *
 * @author liutao
 *
 */
@RestController
@RequestMapping("/route")
public class DynamicRouteController {

    @Resource
    private DynamicRouteService dynamicRouteService;

    /**
     * 路由列表
     * @param data 查询参数
     * @return ResponseData
     */
    @PostMapping("/list")
    public ResponseData<PageInfo<GatewayRouteDto>> pageList(@RequestBody QueryCriteriaBean data) {
        PageInfo<GatewayRouteDto> page = dynamicRouteService.pageList(data);
        return ResponseData.success(page);
    }

    /**
     * 获取路由
     * @param id 编号
     * @return ResponseData
     */
    @GetMapping("/get/{id}")
    public ResponseData<GatewayRouteDto> get(@PathVariable String id) {
        return ResponseData.success(dynamicRouteService.getById(id));
    }

    /**
     * 增加路由
     * @param gatewayRouteVo 路由对象
     * @return ResponseData<GatewayRouteDto>
     */
    @PostMapping("/add")
    public ResponseData<GatewayRouteDto> add(@Validated @RequestBody GatewayRouteVo gatewayRouteVo) {
        if (gatewayRouteVo != null) {
            GatewayRouteDto result = dynamicRouteService.save(gatewayRouteVo);
            return ResponseData.success(result);
        }
        return ResponseData.error(ResultEnum.ERROR.getName());
    }

    /**
     * 刷新路由配置
     * @return ResponseData
     */
    @GetMapping("/refresh")
    public ResponseData<List<GatewayRouteDto>> refresh() {
        List<GatewayRouteDto> list = dynamicRouteService.refreshRoute();
        ResponseData<List<GatewayRouteDto>> result = new ResponseData<>();
        result.setResult(list);
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMessage(ResultEnum.SUCCESS.getName());
        return result;
    }

    /**
     * 更新路由
     * @param gatewayRouteVo 路由对象
     * @return ResponseData<GatewayRouteDto>
     */
    @PostMapping("/update")
    public ResponseData<GatewayRouteDto> update(@Validated @RequestBody GatewayRouteVo gatewayRouteVo) {
        if (gatewayRouteVo != null) {
            GatewayRouteDto result = dynamicRouteService.update(gatewayRouteVo);
            return ResponseData.success(result);
        }
        return ResponseData.error(ResultEnum.ERROR.getName());
    }

    /**
     * 更新路由状态
     * @param id 编号
     * @param status 状态
     * @return ResponseData<Boolean>
     */
    @GetMapping("/status/{id}/{status}")
    public ResponseData<Boolean> updateRouteStatus(@PathVariable String id, @PathVariable StatusEnum status) {
        Boolean result = dynamicRouteService.editStatus(id, status);
        return ResponseData.success(result);
    }

    /**
     * 删除路由
     * @param ids 编号
     * @return ResponseData<List<Boolean>>
     */
    @DeleteMapping("/delete")
    public ResponseData<List<Boolean>> delete(@RequestBody String... ids) {
        List<Boolean> list = Arrays.stream(ids).map(id -> dynamicRouteService.delete(id)).collect(Collectors.toList());
        ResponseData<List<Boolean>> result = new ResponseData<>();
        result.setResult(list);
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMessage(ResultEnum.SUCCESS.getName());
        return result;
    }

}
