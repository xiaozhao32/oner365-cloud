package com.oner365.sys.controller.system;

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
import com.google.common.collect.Maps;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.entity.SysMenuOperation;
import com.oner365.sys.service.ISysMenuOperationService;

/**
 * 菜单操作权限
 * 
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/menuOperation")
public class SysMenuOperationController extends BaseController {

    @Autowired
    private ISysMenuOperationService menuOperationService;

    /**
     * 保存
     * 
     * @param paramJson 操作对象
     * @return Map<String, Object>
     */
    @PutMapping("/save")
    public Map<String, Object> save(@RequestBody JSONObject paramJson) {
        SysMenuOperation sysMenuOperation = JSON.toJavaObject(paramJson, SysMenuOperation.class);
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, PublicConstants.ERROR_CODE);
        if (sysMenuOperation != null) {
            SysMenuOperation entity = menuOperationService.save(sysMenuOperation);

            result.put(PublicConstants.CODE, PublicConstants.SUCCESS_CODE);
            result.put(PublicConstants.MSG, entity);
        }
        return result;
    }

    /**
     * 获取信息
     * 
     * @param id 编号
     * @return SysMenuOperation
     */
    @GetMapping("/get/{id}")
    public SysMenuOperation getById(@PathVariable String id) {
        return menuOperationService.getById(id);
    }

    /**
     * 列表
     * 
     * @param data 查询参数
     * @return Page<SysMenuOperation>
     */
    @PostMapping("/list")
    public Page<SysMenuOperation> findList(@RequestBody QueryCriteriaBean data) {
        return menuOperationService.pageList(data);
    }

    /**
     * 删除
     * 
     * @param ids 编号
     * @return Integer
     */
    @DeleteMapping("/delete")
    public Integer delete(@RequestBody String... ids) {
        int code = 0;
        for (String id : ids) {
            code = menuOperationService.deleteById(id);
        }
        return code;
    }

    /**
     * 判断是否存在
     * 
     * @param paramJson 参数
     * @return Map<String, Object>
     */
    @PostMapping("/checkCode")
    public Map<String, Object> checkCode(@RequestBody JSONObject paramJson) {
        String id = paramJson.getString(SysConstants.ID);
        String type = paramJson.getString(SysConstants.CODE);

        long code = menuOperationService.checkCode(id, type);
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, code);
        return result;
    }
}
