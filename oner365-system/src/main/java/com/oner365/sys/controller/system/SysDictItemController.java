package com.oner365.sys.controller.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.oner365.common.ResponseResult;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.AttributeBean;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dto.SysDictItemDto;
import com.oner365.sys.dto.SysDictItemTypeDto;
import com.oner365.sys.service.ISysDictItemService;
import com.oner365.sys.service.ISysDictItemTypeService;
import com.oner365.sys.vo.SysDictItemTypeVo;
import com.oner365.sys.vo.SysDictItemVo;
import com.oner365.sys.vo.check.CheckCodeVo;
import com.oner365.sys.vo.check.CheckTypeCodeVo;

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
   * 获取类别列表
   *
   * @param data 查询参数
   * @return PageInfo<SysDictItemTypeDto>
   */
  @PostMapping("/type/list")
  public PageInfo<SysDictItemTypeDto> findTypeList(@RequestBody QueryCriteriaBean data) {
    return sysDictItemTypeService.pageList(data);
  }

  /**
   * 获取类别列表
   *
   * @param codes 参数
   * @return List<SysDictItemTypeDto>
   */
  @PostMapping("/type/codes/list")
  public List<SysDictItemTypeDto> findListByCode(@RequestBody String... codes) {
    return sysDictItemTypeService.findListByCodes(Arrays.asList(codes));
  }
  
  /**
   * 获取类别
   *
   * @param id 编号
   * @return SysDictItemTypeDto
   */
  @GetMapping("/type/get/{id}")
  public SysDictItemTypeDto getTypeById(@PathVariable String id) {
    return sysDictItemTypeService.getById(id);
  }
  
  /**
   * 判断类别id 类别是否存在
   *
   * @param checkCodeVo 查询参数
   * @return Long
   */
  @PostMapping("/type/check")
  public Long checkTypeCode(@RequestBody CheckCodeVo checkCodeVo) {
    if (checkCodeVo != null) {
      return sysDictItemTypeService.checkCode(checkCodeVo.getId(), checkCodeVo.getCode());
    }
    return Long.valueOf(ResultEnum.ERROR.getCode());
  }
  
  /**
   * 获取类别中字典列表
   *
   * @param typeId 类型id
   * @return List<SysDictItemDto>
   */
  @GetMapping("/item/type/{typeId}")
  public List<SysDictItemDto> findTypeInfoById(@PathVariable String typeId) {
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
  @PostMapping("/item/type/ids")
  public Map<String, Object> findItemByTypeIds(@RequestBody String... typeIds) {
    Map<String, Object> result = new HashMap<>(10);
    Arrays.stream(typeIds).forEach(typeId -> {
      QueryCriteriaBean data = new QueryCriteriaBean();
      List<AttributeBean> whereList = new ArrayList<>();
      AttributeBean attribute = new AttributeBean(SysConstants.TYPE_ID, typeId);
      whereList.add(attribute);
      data.setWhereList(whereList);
      List<SysDictItemDto> itemList = sysDictItemService.findList(data);
      result.put(typeId, itemList);
    });
    return result;
  }
  
  /**
   * 修改状态
   *
   * @param id     主键
   * @param status 状态
   * @return Integer
   */
  @PostMapping("/type/status/{id}")
  public Integer editTypeStatus(@PathVariable String id, @RequestParam("status") StatusEnum status) {
    return sysDictItemTypeService.editStatus(id, status);
  }
  
  /**
   * 字典类别保存
   *
   * @param sysDictItemTypeVo 字典类别对象
   * @return ResponseResult<SysDictItemTypeDto>
   */
  @PutMapping("/type/save")
  public ResponseResult<SysDictItemTypeDto> saveDictItemType(@RequestBody SysDictItemTypeVo sysDictItemTypeVo) {
    if (sysDictItemTypeVo != null) {
      SysDictItemTypeDto entity = sysDictItemTypeService.save(sysDictItemTypeVo);
      return ResponseResult.success(entity);
    }
    return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }
  
  /**
   * 删除字典类别
   *
   * @param ids 字典编号
   * @return Integer
   */
  @DeleteMapping("/type/delete")
  public Integer deleteItemType(@RequestBody String... ids) {
    int code = 0;
    for (String id : ids) {
      code = sysDictItemTypeService.deleteById(id);
    }
    return code;
  }
  
  /**
   * 导出字典类型Excel
   * 
   * @param data 参数
   * @return ResponseEntity<byte[]>
   */
  @PostMapping("/type/export")
  public ResponseEntity<byte[]> exportItemType(@RequestBody QueryCriteriaBean data) {
    List<SysDictItemTypeDto> list = sysDictItemTypeService.findList(data);

    String[] titleKeys = new String[] { "编号", "类型名称", "类型标识", "描述", "排序", "状态" };
    String[] columnNames = { "id", "typeName", "typeCode", "typeDes", "typeOrder", "status" };

    String fileName = SysDictItemTypeDto.class.getSimpleName() + System.currentTimeMillis();
    return exportExcel(fileName, titleKeys, columnNames, list);
  }

  /**
   * 获取字典列表
   *
   * @param data 查询参数
   * @return PageInfo<SysDictItemDto>
   */
  @PostMapping("/item/list")
  public PageInfo<SysDictItemDto> findItemList(@RequestBody QueryCriteriaBean data) {
    return sysDictItemService.pageList(data);
  }
  
  /**
   * 获取字典信息
   *
   * @param id 字典编号
   * @return SysDictItemDto
   */
  @GetMapping("/item/get/{id}")
  public SysDictItemDto getItemById(@PathVariable String id) {
    return sysDictItemService.getById(id);
  }
  
  /**
   * 判断类别id 字典是否存在
   *
   * @param checkTypeCodeVo 查询参数
   * @return Long
   */
  @PostMapping("/item/check")
  public Long checkCode(@RequestBody CheckTypeCodeVo checkTypeCodeVo) {
    if (checkTypeCodeVo != null) {
      return sysDictItemService.checkCode(checkTypeCodeVo.getId(), checkTypeCodeVo.getTypeId(),
          checkTypeCodeVo.getCode());
    }
    return Long.valueOf(ResultEnum.ERROR.getCode());
  }

  /**
   * 修改状态
   *
   * @param id     主键
   * @param status 状态
   * @return Integer
   */
  @PostMapping("/item/status/{id}")
  public Integer editItemStatus(@PathVariable String id, @RequestParam("status") StatusEnum status) {
    return sysDictItemService.editStatus(id, status);
  }

  /**
   * 保存字典信息
   *
   * @param sysDictItemVo 字典对象
   * @return ResponseResult<SysDictItemDto>
   */
  @PutMapping("/item/save")
  public ResponseResult<SysDictItemDto> saveDictItem(@RequestBody SysDictItemVo sysDictItemVo) {
    if (sysDictItemVo != null) {
      SysDictItemDto entity = sysDictItemService.save(sysDictItemVo);
      return ResponseResult.success(entity);
    }
    return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }

  /**
   * 删除字典信息
   *
   * @param ids 编号
   * @return Integer
   */
  @DeleteMapping("/item/delete")
  public Integer deleteItem(@RequestBody String... ids) {
    int code = 0;
    for (String id : ids) {
      code = sysDictItemService.deleteById(id);
    }
    return code;
  }

  /**
   * 导出字典Excel
   * 
   * @param data 查询参数
   * @return ResponseEntity<byte[]>
   */
  @PostMapping("/item/export")
  public ResponseEntity<byte[]> exportItem(@RequestBody QueryCriteriaBean data) {
    List<SysDictItemDto> list = sysDictItemService.findList(data);

    String[] titleKeys = new String[] { "编号", "类型标识", "字典名称", "字典标识", "排序", "状态" };
    String[] columnNames = { "id", "typeId", "itemName", "itemCode", "itemOrder", "status" };

    String fileName = SysDictItemDto.class.getSimpleName() + System.currentTimeMillis();
    return exportExcel(fileName, titleKeys, columnNames, list);
  }
}
