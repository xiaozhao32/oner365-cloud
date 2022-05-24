package com.oner365.swagger.controller.system;

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

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.swagger.client.system.ISystemDictClient;
import com.oner365.swagger.dto.SysDictItemDto;
import com.oner365.swagger.dto.SysDictItemTypeDto;
import com.oner365.swagger.vo.SysDictItemTypeVo;
import com.oner365.swagger.vo.SysDictItemVo;
import com.oner365.swagger.vo.check.CheckCodeVo;
import com.oner365.swagger.vo.check.CheckTypeCodeVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 字典信息
 *
 * @author zhaoyong
 */
@RestController
@Api(tags = "系统管理 - 字典信息")
@RequestMapping("/system/dict")
public class SysDictItemController extends BaseController {
  
  @Autowired
  private ISystemDictClient client;

  /**
   * 获取类别列表
   *
   * @param data 查询参数
   * @return ResponseData<PageInfo<SysDictItemTypeDto>>
   */
  @ApiOperation("1.获取类别列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/type/list")
  public ResponseData<PageInfo<SysDictItemTypeDto>> findTypeList(@RequestBody QueryCriteriaBean data) {
    return client.findTypeList(data);
  }
  
  /**
   * 获取指定类别列表
   *
   * @param codes 参数
   * @return ResponseData<List<SysDictItemTypeDto>>
   */
  @ApiOperation("2.获取指定类别列表")
  @ApiOperationSupport(order = 2)
  @PostMapping("/type/codes/list")
  public ResponseData<List<SysDictItemTypeDto>> findListByCode(@RequestBody String... codes) {
    return client.findListByCode(codes);
  }
  
  /**
   * 获取类别
   *
   * @param id 编号
   * @return ResponseData<SysDictItemTypeDto>
   */
  @ApiOperation("3.按id查询类别")
  @ApiOperationSupport(order = 3)
  @GetMapping("/type/get/{id}")
  public ResponseData<SysDictItemTypeDto> getTypeById(@PathVariable String id) {
    return client.getTypeById(id);
  }
  
  /**
   * 判断类别id 类别是否存在
   *
   * @param checkCodeVo 查询参数
   * @return ResponseData<Boolean>
   */
  @ApiOperation("4.判断字典类别是否存在")
  @ApiOperationSupport(order = 4)
  @PostMapping("/type/check")
  public ResponseData<Boolean> checkTypeCode(@RequestBody CheckCodeVo checkCodeVo) {
    return client.checkTypeCode(checkCodeVo);
  }
  
  /**
   * 获取类别中字典列表
   *
   * @param typeId 类型id
   * @return ResponseData<List<SysDictItemDto>>
   */
  @ApiOperation("5.按类别id查询列表")
  @ApiOperationSupport(order = 5)
  @GetMapping("/item/type/{typeId}")
  public ResponseData<List<SysDictItemDto>> findTypeInfoById(@PathVariable String typeId) {
    return client.findTypeInfoById(typeId);
  }
  
  /**
   * 获取类别字典信息
   *
   * @param typeIds 字典参数
   * @return ResponseData<Map<String, Object>>
   */
  @ApiOperation("6.按类别编码查询字典列表")
  @ApiOperationSupport(order = 6)
  @PostMapping("/item/type/ids")
  public ResponseData<Map<String, Object>> findItemByTypeIds(@RequestBody String... typeIds) {
    return client.findItemByTypeIds(typeIds);
  }
  
  /**
   * 修改状态
   *
   * @param id     主键
   * @param status 状态
   * @return ResponseData<Boolean>
   */
  @ApiOperation("7.修改类别状态")
  @ApiOperationSupport(order = 7)
  @PostMapping("/type/status/{id}")
  public ResponseData<Boolean> editTypeStatus(@PathVariable String id, @RequestParam("status") StatusEnum status) {
    return client.editTypeStatus(id, status);
  }
  
  /**
   * 字典类别保存
   *
   * @param sysDictItemTypeVo 字典类别对象
   * @return ResponseData<ResponseResult<SysDictItemTypeDto>>
   */
  @ApiOperation("8.字典类别保存")
  @ApiOperationSupport(order = 8)
  @PutMapping("/type/save")
  public ResponseData<ResponseResult<SysDictItemTypeDto>> saveDictItemType(@RequestBody SysDictItemTypeVo sysDictItemTypeVo) {
    return client.saveDictItemType(sysDictItemTypeVo);
  }
  
  /**
   * 删除字典类别
   *
   * @param ids 字典编号
   * @return ResponseData<List<Boolean>>
   */
  @ApiOperation("9.删除字典类别")
  @ApiOperationSupport(order = 9)
  @DeleteMapping("/type/delete")
  public ResponseData<List<Boolean>> deleteItemType(@RequestBody String... ids) {
    return client.deleteItemType(ids);
  }
  
  /**
   * 导出字典类型Excel
   *
   * @param data 参数
   * @return ResponseEntity<byte[]>
   */
  @ApiOperation("10.导出字典类别")
  @ApiOperationSupport(order = 10)
  @PostMapping("/type/export")
  public ResponseEntity<byte[]> exportItemType(@RequestBody QueryCriteriaBean data) {
    return client.exportItemType(data);
  }
  
  /**
   * 获取字典列表
   *
   * @param data 查询参数
   * @return ResponseData<PageInfo<SysDictItemDto>>
   */
  @ApiOperation("11.获取字典列表")
  @ApiOperationSupport(order = 11)
  @PostMapping("/item/list")
  public ResponseData<PageInfo<SysDictItemDto>> findItemList(@RequestBody QueryCriteriaBean data) {
    return client.findItemList(data);
  }
  
  /**
   * 获取字典信息
   *
   * @param id 字典编号
   * @return ResponseData<SysDictItemDto>
   */
  @ApiOperation("12.按id查询字典")
  @ApiOperationSupport(order = 12)
  @GetMapping("/item/get/{id}")
  public ResponseData<SysDictItemDto> getItemById(@PathVariable String id) {
    return client.getItemById(id);
  }
  
  /**
   * 判断类别id 字典是否存在
   *
   * @param checkTypeCodeVo 查询参数
   * @return ResponseData<Boolean>
   */
  @ApiOperation("13.判断字典是否存在")
  @ApiOperationSupport(order = 13)
  @PostMapping("/item/check")
  public ResponseData<Boolean> checkCode(@RequestBody CheckTypeCodeVo checkTypeCodeVo) {
    return client.checkCode(checkTypeCodeVo);
  }
  
  /**
   * 修改状态
   *
   * @param id     主键
   * @param status 状态
   * @return ResponseData<Boolean>
   */
  @ApiOperation("14.修改字典状态")
  @ApiOperationSupport(order = 14)
  @PostMapping("/item/status/{id}")
  public ResponseData<Boolean> editItemStatus(@PathVariable String id, @RequestParam("status") StatusEnum status) {
    return client.editItemStatus(id, status);
  }
  
  /**
   * 保存字典信息
   *
   * @param sysDictItemVo 字典对象
   * @return ResponseData<ResponseResult<SysDictItemDto>>
   */
  @ApiOperation("15.保存字典")
  @ApiOperationSupport(order = 15)
  @PutMapping("/item/save")
  public ResponseData<ResponseResult<SysDictItemDto>> saveDictItem(@RequestBody SysDictItemVo sysDictItemVo) {
    return client.saveDictItem(sysDictItemVo);
  }
  
  /**
   * 删除字典信息
   *
   * @param ids 编号
   * @return ResponseData<List<Boolean>>
   */
  @ApiOperation("16.删除字典")
  @ApiOperationSupport(order = 16)
  @DeleteMapping("/item/delete")
  public ResponseData<List<Boolean>> deleteItem(@RequestBody String... ids) {
    return client.deleteItem(ids);
  }
  
  /**
   * 导出字典Excel
   *
   * @param data 查询参数
   * @return ResponseEntity<byte[]>
   */
  @ApiOperation("17.导出字典")
  @ApiOperationSupport(order = 17)
  @PostMapping("/item/export")
  public ResponseEntity<byte[]> exportItem(@RequestBody QueryCriteriaBean data) {
    return client.exportItem(data);
  }
  
}
