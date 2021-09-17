package com.oner365.sys.controller.system;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.query.AttributeBean;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.entity.SysMenuType;
import com.oner365.sys.service.ISysMenuTypeService;

/**
 * 菜单类型管理
 * 
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/menuType")
public class SysMenuTypeController extends BaseController {

    @Autowired
    private ISysMenuTypeService menuTypeService;

    /**
     * 保存
     * 
     * @param paramJson 菜单类型对象
     * @return Map<String, Object>
     */
    @PutMapping("/save")
    public Map<String, Object> save(@RequestBody JSONObject paramJson) {
        SysMenuType sysMenuType = JSON.toJavaObject(paramJson, SysMenuType.class);
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, PublicConstants.ERROR_CODE);
        if (sysMenuType != null) {
            SysMenuType entity = menuTypeService.save(sysMenuType);

            result.put(PublicConstants.CODE, PublicConstants.SUCCESS_CODE);
            result.put(PublicConstants.MSG, entity);
        }
        return result;
    }

    /**
     * 获取信息
     * 
     * @param id 编号
     * @return SysMenuType
     */
    @GetMapping("/get/{id}")
    public SysMenuType get(@PathVariable String id) {
        return menuTypeService.getById(id);
    }

    /**
     * 列表
     * 
     * @param data 参数
     * @return Page<SysMenuType>
     */
    @PostMapping("/list")
    public Page<SysMenuType> list(@RequestBody QueryCriteriaBean data) {
        return menuTypeService.pageList(data);
    }

    /**
     * 列表
     * 
     * @return List<SysMenuType>
     */
    @GetMapping("/findAll")
    public List<SysMenuType> findAll() {
        QueryCriteriaBean data = new QueryCriteriaBean();
        List<AttributeBean> whereList = new ArrayList<>();
        AttributeBean attribute = new AttributeBean(SysConstants.STATUS, PublicConstants.STATUS_YES);
        whereList.add(attribute);
        data.setWhereList(whereList);
        return menuTypeService.findList(data);
    }

    /**
     * 修改状态
     * 
     * @param id     主键
     * @param status 状态
     * @return Integer
     */
    @PostMapping("/editStatusById/{id}")
    public Integer editStatusById(@PathVariable String id, @RequestParam("status") String status) {
        return menuTypeService.editStatusById(id, status);
    }

    /**
     * 判断是否存在
     * 
     * @param data 参数
     * @return Map<String, Object>
     */
    @PostMapping("/checkCode")
    public Map<String, Object> checkCode(@RequestBody JSONObject data) {
        String id = data.getString(SysConstants.ID);
        String code = data.getString(PublicConstants.CODE);
        long check = menuTypeService.checkCode(id, code);

        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, check);
        return result;
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
            code = menuTypeService.deleteById(id);
        }
        return code;
    }
}
