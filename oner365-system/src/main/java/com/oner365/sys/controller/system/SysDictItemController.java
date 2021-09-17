package com.oner365.sys.controller.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
import com.oner365.sys.entity.SysDictItem;
import com.oner365.sys.entity.SysDictItemType;
import com.oner365.sys.service.ISysDictItemService;
import com.oner365.sys.service.ISysDictItemTypeService;

/**
 * 字典信息
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/dict")
public class SysDictItemController extends BaseController {

    @Autowired
    private ISysDictItemTypeService sysDictItemTypeService;

    @Autowired
    private ISysDictItemService sysDictItemService;

    /**
     * 字典类别保存
     *
     * @param paramJson 字典类别对象
     * @return Map<String, Object>
     */
    @PutMapping("/saveDictItemType")
    public Map<String, Object> saveDictItemType(@RequestBody JSONObject paramJson) {
        SysDictItemType sysDictItemType = JSON.toJavaObject(paramJson, SysDictItemType.class);
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, PublicConstants.ERROR_CODE);
        if (sysDictItemType != null) {
            SysDictItemType entity = sysDictItemTypeService.save(sysDictItemType);
            result.put(PublicConstants.CODE, PublicConstants.SUCCESS_CODE);
            result.put(PublicConstants.MSG, entity);
        }
        return result;
    }

    /**
     * 判断类别id 是否存在
     *
     * @param paramJson 参数
     * @return Map<String, Object>
     */
    @PostMapping("/checkTypeCode")
    public Map<String, Object> checkTypeCode(@RequestBody JSONObject paramJson) {
        String id = paramJson.getString(SysConstants.ID);
        String code = paramJson.getString(PublicConstants.CODE);

        long check = sysDictItemTypeService.checkCode(id, code);
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, check);
        return result;
    }
    
    /**
     * 判断类别id 是否存在
     *
     * @param paramJson 参数
     * @return Map<String, Object>
     */
    @PostMapping("/checkCode")
    public Map<String, Object> checkCode(@RequestBody JSONObject paramJson) {
        String id = paramJson.getString(SysConstants.ID);
        String typeId = paramJson.getString(SysConstants.TYPE_ID);
        String code = paramJson.getString(PublicConstants.CODE);

        long check = sysDictItemService.checkCode(id, typeId, code);
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, check);
        return result;
    }

    /**
     * 获取类别
     *
     * @param id 编号
     * @return SysDictItemType
     */
    @GetMapping("/getTypeById/{id}")
    public SysDictItemType getTypeById(@PathVariable String id) {
        return sysDictItemTypeService.getById(id);
    }

    /**
     * 获取类别中字典列表
     *
     * @param typeId 类型id
     * @return List<SysDictItem>
     */
    @GetMapping("/findTypeInfoById/{typeId}")
    public List<SysDictItem> findTypeInfoById(@PathVariable String typeId) {
        QueryCriteriaBean data = new QueryCriteriaBean();
        List<AttributeBean> whereList = new ArrayList<>();
        AttributeBean attribute = new AttributeBean(SysConstants.TYPE_ID, typeId);
        whereList.add(attribute);
        data.setWhereList(whereList);
        return sysDictItemService.findList(data);
    }

    /**
     * 获取类别字典信息
     *
     * @param typeIds 字典参数
     * @return Map<String, Object>
     */
    @PostMapping("/findItemByTypeIds")
    public Map<String, Object> findItemByTypeIds(@RequestBody String... typeIds) {
        Map<String, Object> result = Maps.newHashMap();
        for (String typeId : typeIds) {
            QueryCriteriaBean data = new QueryCriteriaBean();
            List<AttributeBean> whereList = new ArrayList<>();
            AttributeBean attribute = new AttributeBean(SysConstants.TYPE_ID, typeId);
            whereList.add(attribute);
            data.setWhereList(whereList);
            List<SysDictItem> itemList = sysDictItemService.findList(data);
            result.put(typeId, itemList);
        }
        return result;
    }

    /**
     * 获取类别列表
     *
     * @param data 查询参数
     * @return Page<SysDictItemType>
     */
    @PostMapping("/findTypeList")
    public Page<SysDictItemType> findTypeList(@RequestBody QueryCriteriaBean data) {
        return sysDictItemTypeService.pageList(data);
    }

    /**
     * 修改状态
     *
     * @param id     主键
     * @param status 状态
     * @return Integer
     */
    @PostMapping("/editTypeStatus/{id}")
    public Integer editTypeStatus(@PathVariable String id, @RequestParam("status") String status) {
        return sysDictItemTypeService.editStatus(id, status);
    }

    /**
     * 修改状态
     *
     * @param id     主键
     * @param status 状态
     * @return Integer
     */
    @PostMapping("/editItemStatus/{id}")
    public Integer editItemStatus(@PathVariable String id, @RequestParam("status") String status) {
        return sysDictItemService.editStatus(id, status);
    }

    /**
     * 获取字典列表
     *
     * @param data 查询参数
     * @return Page<SysDictItem>
     */
    @PostMapping("/findItemList")
    public Page<SysDictItem> findItemList(@RequestBody QueryCriteriaBean data) {
        return sysDictItemService.pageList(data);
    }

    /**
     * 保存字典信息
     *
     * @param paramJson 字典对象
     * @return Map<String, Object>
     */
    @PutMapping("/saveDictItem")
    public Map<String, Object> saveDictItem(@RequestBody JSONObject paramJson) {
        SysDictItem sysDictItem = JSON.toJavaObject(paramJson, SysDictItem.class);
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, PublicConstants.ERROR_CODE);
        if (sysDictItem != null) {
            SysDictItem entity = sysDictItemService.save(sysDictItem);

            result.put(PublicConstants.CODE, PublicConstants.SUCCESS_CODE);
            result.put(PublicConstants.MSG, entity);
        }
        return result;
    }

    /**
     * 获取字典信息
     *
     * @param id 字典编号
     * @return SysDictItem
     */
    @GetMapping("/getItemById/{id}")
    public SysDictItem getItemById(@PathVariable String id) {
        return sysDictItemService.getById(id);
    }

    /**
     * 删除字典信息
     *
     * @param ids 编号
     * @return Integer
     */
    @DeleteMapping("/deleteItem")
    public Integer deleteItem(@RequestBody String... ids) {
        int code = 0;
        for (String id : ids) {
            code = sysDictItemService.deleteById(id);
        }
        return code;
    }

    /**
     * 删除字典类别
     *
     * @param ids 字典编号
     * @return Integer
     */
    @DeleteMapping("/deleteItemType")
    public Integer deleteItemType(@RequestBody String... ids) {
        int code = 0;
        for (String id : ids) {
            code = sysDictItemTypeService.deleteById(id);
        }
        return code;
    }

    /**
     * 获取类别列表
     *
     * @param codes 参数
     * @return Map<String, Object>
     */
    @PostMapping("/findListByCodes")
    public Map<String, Object> findListByCode(@RequestBody String... codes) {
        List<SysDictItemType> list = sysDictItemTypeService.findListByCodes(Arrays.asList(codes));
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.PARAM_LIST, list);
        return result;
    }

    /**
     * 导出字典类型Excel
     * 
     * @param data 参数
     * @return ResponseEntity<byte[]>
     */
    @PostMapping("/exportItemType")
    public ResponseEntity<byte[]> exportItemType(@RequestBody QueryCriteriaBean data) {
        List<SysDictItemType> list = sysDictItemTypeService.findList(data);

        String[] titleKeys = new String[] { "编号", "类型名称", "类型标识", "描述", "排序", "状态" };
        String[] columnNames = { "id", "typeName", "typeCode", "typeDes", "typeOrder", "status" };

        String fileName = SysDictItemType.class.getSimpleName() + System.currentTimeMillis();
        return exportExcel(fileName, titleKeys, columnNames, list);
    }

    /**
     * 导出字典Excel
     * 
     * @param data 查询参数
     * @return ResponseEntity<byte[]>
     */
    @PostMapping("/exportItem")
    public ResponseEntity<byte[]> exportItem(@RequestBody QueryCriteriaBean data) {
        List<SysDictItem> list = sysDictItemService.findList(data);

        String[] titleKeys = new String[] { "编号", "类型标识", "字典名称", "字典标识", "排序", "状态" };
        String[] columnNames = { "id", "typeId", "itemName", "itemCode", "itemOrder", "status" };

        String fileName = SysDictItem.class.getSimpleName() + System.currentTimeMillis();
        return exportExcel(fileName, titleKeys, columnNames, list);
    }
}
