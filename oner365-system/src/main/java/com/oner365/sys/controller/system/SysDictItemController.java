package com.oner365.sys.controller.system;

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
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oner365.common.constants.PublicConstants;
import com.oner365.controller.BaseController;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.entity.SysDictItem;
import com.oner365.sys.entity.SysDictItemType;
import com.oner365.sys.service.ISysDictItemService;
import com.oner365.sys.service.ISysDictItemTypeService;
import com.google.common.collect.Maps;

/**
 * 字典信息
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
    @PostMapping("/checkTypeId")
    public Map<String, Object> checkTypeId(@RequestBody JSONObject paramJson) {
        String id = paramJson.getString(SysConstants.ID);
        String code = paramJson.getString(PublicConstants.CODE);
        int check = sysDictItemTypeService.checkTypeId(id, code);
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
        JSONObject paramJson = new JSONObject();
        paramJson.put(SysConstants.TYPE_ID, typeId);
        return sysDictItemService.findList(paramJson);
    }

    /**
     * 获取类别字典信息
     *
     * @param paramJson 字典参数
     * @return Map<String, Object>
     */
    @PostMapping("/findTypeInfoByCodes")
    public Map<String, Object> findTypeInfoByCodes(@RequestBody JSONObject paramJson) {
        JSONArray array = paramJson.getJSONArray("codes");
        Map<String, Object> result = Maps.newHashMap();
        array.forEach(obj -> {
            JSONObject json = new JSONObject();
            json.put(SysConstants.TYPE_ID, obj);
            List<SysDictItem> itemList = sysDictItemService.findList(json);
            result.put((String)obj, itemList);
        });
        return result;
    }

    /**
     * 获取类别列表
     *
     * @param paramJson 参数
     * @return Page<SysDictItemType>
     */
    @PostMapping("/findTypeList")
    public Page<SysDictItemType> findTypeList(@RequestBody JSONObject paramJson) {
        return sysDictItemTypeService.pageList(paramJson);
    }

    /**
     * 修改状态
     *
     * @param json 参数
     * @return Map<String, Object>
     */
    @PostMapping("/editTypeStatus")
    public Map<String, Object> editTypeStatus(@RequestBody JSONObject json) {
        String status = json.getString(SysConstants.STATUS);
        String id = json.getString(SysConstants.ID);
        Integer code = sysDictItemTypeService.editStatus(id, status);
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, code);
        return result;
    }

    /**
     * 修改状态
     *
     * @param json 参数
     * @return Map<String, Object>
     */
    @PostMapping("/editItemStatus")
    public Map<String, Object> editItemStatus(@RequestBody JSONObject json) {
        String status = json.getString(SysConstants.STATUS);
        String id = json.getString(SysConstants.ID);
        Integer code = sysDictItemService.editStatus(id, status);
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, code);
        return result;
    }

    /**
     * 获取字典列表
     *
     * @param paramJson 参数
     * @return Page<SysDictItem>
     */
    @PostMapping("/findItemList")
    public Page<SysDictItem> findItemList(@RequestBody JSONObject paramJson) {
        return sysDictItemService.pageList(paramJson);
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
     * @return Map<String, Object>
     */
    @DeleteMapping("/deleteItem")
    public Map<String, Object> deleteItem(@RequestBody String... ids) {
        int code = 0;
        for (String id : ids) {
            code = sysDictItemService.deleteById(id);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, code);
        return result;
    }

    /**
     * 删除字典类别
     *
     * @param ids 字典编号
     * @return Map<String, Object>
     */
    @DeleteMapping("/deleteItemType")
    public Map<String, Object> deleteItemType(@RequestBody String... ids) {
        int code = 0;
        for (String id : ids) {
            code = sysDictItemTypeService.deleteById(id);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, code);
        return result;
    }

    /**
     * 获取类别列表
     *
     * @param json 参数
     * @return Map<String, Object>
     */
    @PostMapping("/findListByCodes")
    public Map<String, Object> findListByCode(@RequestBody JSONObject json) {
        List<SysDictItemType> list = sysDictItemTypeService.findListByCodes(json);
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.PARAM_LIST, list);
        return result;
    }

    /**
     * 导出字典类型Excel
     * @param paramJson 参数
     * @return ResponseEntity<byte[]>
     */
    @PostMapping("/exportItemType")
    public ResponseEntity<byte[]> exportItemType(@RequestBody JSONObject paramJson){
        List<SysDictItemType> list = sysDictItemTypeService.findList(paramJson);

        String[] titleKeys = new String[]{"编号","类型名称","类型标识","描述","排序","状态"};
        String[] columnNames = {"id","typeName","typeCode","typeDes","typeOrder","status"};

        String fileName = SysDictItemType.class.getSimpleName() + System.currentTimeMillis();
        return exportExcel(fileName, titleKeys, columnNames, list);
    }

    /**
     * 导出字典Excel
     * @param paramJson 参数
     * @return ResponseEntity<byte[]>
     */
    @PostMapping("/exportItem")
    public ResponseEntity<byte[]> exportItem(@RequestBody JSONObject paramJson){
        List<SysDictItem> list = sysDictItemService.findList(paramJson);

        String[] titleKeys = new String[]{"编号","类型标识","字典名称","字典标识","排序","状态"};
        String[] columnNames = {"id","typeId","itemName","itemCode","itemOrder","status"};

        String fileName = SysDictItem.class.getSimpleName() + System.currentTimeMillis();
        return exportExcel(fileName, titleKeys, columnNames, list);
    }
}
