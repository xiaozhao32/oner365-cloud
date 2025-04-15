package com.oner365.swagger.client.system;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.SysDictItemDto;
import com.oner365.swagger.dto.SysDictItemTypeDto;
import com.oner365.swagger.vo.SysDictItemTypeVo;
import com.oner365.swagger.vo.SysDictItemVo;
import com.oner365.swagger.vo.check.CheckCodeVo;
import com.oner365.swagger.vo.check.CheckTypeCodeVo;

/**
 * 系统服务 - 字典管理
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_SYSTEM, contextId = PathConstants.CONTEXT_SYSTEM_DICT_ID)
public interface ISystemDictClient {

  /**
   * 获取类别分页
   *
   * @param data 查询参数
   * @return ResponseData<PageInfo<SysDictItemTypeDto>>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_DICT_TYPE_LIST)
  ResponseData<PageInfo<SysDictItemTypeDto>> findTypeList(@RequestBody QueryCriteriaBean data);
  
  /**
   * 获取指定类别列表
   *
   * @param codes 参数
   * @return ResponseData<List<SysDictItemTypeDto>>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_DICT_TYPE_CODES_LIST)
  ResponseData<List<SysDictItemTypeDto>> findListByCode(@RequestBody String... codes);
  
  /**
   * 获取类别
   *
   * @param id 编号
   * @return ResponseData<SysDictItemTypeDto>
   */
  @GetMapping(PathConstants.REQUEST_SYSTEM_DICT_TYPE_GET_ID)
  ResponseData<SysDictItemTypeDto> getTypeById(@PathVariable("id") String id);
  
  /**
   * 判断类别id 类别是否存在
   *
   * @param checkCodeVo 查询参数
   * @return ResponseData<Boolean>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_DICT_TYPE_CHECK)
  ResponseData<Boolean> checkTypeCode(@RequestBody CheckCodeVo checkCodeVo);
  
  /**
   * 获取类别中字典列表
   *
   * @param typeId 类型id
   * @return ResponseData<List<SysDictItemDto>>
   */
  @GetMapping(PathConstants.REQUEST_SYSTEM_DICT_ITEM_TYPE)
  ResponseData<List<SysDictItemDto>> findTypeInfoById(@PathVariable("typeId") String typeId);
  
  /**
   * 获取类别字典信息
   *
   * @param typeIds 字典参数
   * @return ResponseData<Map<String, Object>>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_DICT_ITEM_TYPE_IDS)
  ResponseData<Map<String, Object>> findItemByTypeIds(@RequestBody String... typeIds);
  
  /**
   * 修改状态
   *
   * @param id     主键
   * @param status 状态
   * @return ResponseData<Boolean>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_DICT_TYPE_STATUS)
  ResponseData<Boolean> editTypeStatus(@PathVariable("id") String id, @RequestParam("status") StatusEnum status);
  
  /**
   * 字典类别保存
   *
   * @param sysDictItemTypeVo 字典类别对象
   * @return ResponseData<SysDictItemTypeDto>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_DICT_TYPE_SAVE)
  ResponseData<SysDictItemTypeDto> saveDictItemType(@RequestBody SysDictItemTypeVo sysDictItemTypeVo);
  
  /**
   * 删除字典类别
   *
   * @param ids 字典编号
   * @return ResponseData<List<Boolean>>
   */
  @DeleteMapping(PathConstants.REQUEST_SYSTEM_DICT_TYPE_DELETE)
  ResponseData<List<Boolean>> deleteItemType(@RequestBody String... ids);
  
  /**
   * 导出字典类型Excel
   *
   * @param data 参数
   * @return ResponseEntity<byte[]>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_DICT_TYPE_EXPORT)
  ResponseEntity<byte[]> exportItemType(@RequestBody QueryCriteriaBean data);
  
  /**
   * 获取字典列表
   *
   * @param data 查询参数
   * @return ResponseData<PageInfo<SysDictItemDto>>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_DICT_ITEM_LIST)
  ResponseData<PageInfo<SysDictItemDto>> findItemList(@RequestBody QueryCriteriaBean data);
  
  /**
   * 获取字典信息
   *
   * @param id 字典编号
   * @return ResponseData<SysDictItemDto>
   */
  @GetMapping(PathConstants.REQUEST_SYSTEM_DICT_ITEM_GET_ID)
  ResponseData<SysDictItemDto> getItemById(@PathVariable("id") String id);
  
  /**
   * 判断类别id 字典是否存在
   *
   * @param checkTypeCodeVo 查询参数
   * @return ResponseData<Boolean>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_DICT_ITEM_CHECK)
  ResponseData<Boolean> checkCode(@RequestBody CheckTypeCodeVo checkTypeCodeVo);
  
  /**
   * 修改状态
   *
   * @param id     主键
   * @param status 状态
   * @return ResponseData<Boolean>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_DICT_ITEM_STATUS)
  ResponseData<Boolean> editItemStatus(@PathVariable("id") String id, @RequestParam("status") StatusEnum status);
  
  /**
   * 保存字典信息
   *
   * @param sysDictItemVo 字典对象
   * @return ResponseData<SysDictItemDto>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_DICT_ITEM_SAVE)
  ResponseData<SysDictItemDto> saveDictItem(@RequestBody SysDictItemVo sysDictItemVo);
  
  /**
   * 删除字典信息
   *
   * @param ids 编号
   * @return ResponseData<List<Boolean>>
   */
  @DeleteMapping(PathConstants.REQUEST_SYSTEM_DICT_ITEM_DELETE)
  ResponseData<List<Boolean>> deleteItem(@RequestBody String... ids);
  
  /**
   * 导出字典Excel
   *
   * @param data 查询参数
   * @return ResponseEntity<byte[]>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_DICT_ITEM_EXPORT)
  ResponseEntity<byte[]> exportItem(@RequestBody QueryCriteriaBean data);
}
