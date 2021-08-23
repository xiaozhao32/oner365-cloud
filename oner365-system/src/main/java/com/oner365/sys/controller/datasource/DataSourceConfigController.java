package com.oner365.sys.controller.datasource;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oner365.common.constants.PublicConstants;
import com.oner365.controller.BaseController;
import com.oner365.sys.entity.DataSourceConfig;
import com.oner365.sys.service.IDataSourceConfigService;
import com.google.common.collect.Maps;

/**
 * 数据源
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/datasource")
public class DataSourceConfigController extends BaseController {

    @Autowired
    private IDataSourceConfigService service;

    /**
     * 列表
     * @param json 参数
     * @return Page<DataSourceConfig>
     */
    @PostMapping("/list")
    public Page<DataSourceConfig> findList(@RequestBody JSONObject json) {
        return service.pageList(json);
    }

    /**
     * 按id获取信息
     * @param id 编号
     * @return DataSourceConfig
     */
    @GetMapping("/get/{id}")
    public DataSourceConfig get(@PathVariable String id) {
        return service.getById(id);
    }

    /**
     * 按 connectName 获取信息
     * @param connectName 连接名称
     * @return DataSourceConfig
     */
    @GetMapping("/getConnectName")
    public DataSourceConfig getConnectName(String connectName) {
        return service.getConnectName(connectName);
    }

    /**
     * 保存
     * @param paramJson 数据源对象
     * @return Map<String, Object>
     */
    @PutMapping("/save")
    public Map<String, Object> save(@RequestBody JSONObject paramJson) {
        DataSourceConfig dataSourceConfig = JSON.toJavaObject(paramJson, DataSourceConfig.class);
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, PublicConstants.ERROR_CODE);
        if (dataSourceConfig != null) {
            DataSourceConfig entity = service.save(dataSourceConfig);
            
            result.put(PublicConstants.CODE, PublicConstants.SUCCESS_CODE);
            result.put(PublicConstants.MSG, entity);
        }
        return result;
    }

    /**
     * 删除
     * @param ids 编号
     * @return Map<String, Object>
     */
    @DeleteMapping("/delete")
    public Map<String, Object> delete(@RequestBody String... ids) {
        int code = 0;
        for (String id : ids) {
            code = service.deleteById(id);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, code);
        return result;
    }
}
