package com.oner365.swagger.client.system;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.SysMenuTypeDto;
import com.oner365.swagger.vo.SysMenuTypeVo;
import com.oner365.swagger.vo.check.CheckCodeVo;

/**
 * 系统服务 - 菜单类型管理
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_SYSTEM, contextId = PathConstants.CONTEXT_SYSTEM_MENU_TYPE_ID)
public interface ISystemMenuTypeClient {

  /**
   * 列表
   * 
   * @param data 查询参数
   * @return ResponseData<PageInfo<SysMenuTypeDto>>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_MENU_TYPE_LIST)
  ResponseData<PageInfo<SysMenuTypeDto>> list(@RequestBody QueryCriteriaBean data);
  
  /**
   * 获取全部有效类型
   * 
   * @return ResponseData<List<SysMenuTypeDto>>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_MENU_TYPE_ALL)
  ResponseData<List<SysMenuTypeDto>> all();

  /**
   * 按id获取查询
   * 
   * @param id 编号
   * @return ResponseData<SysMenuTypeDto>
   */
  @GetMapping(PathConstants.REQUEST_SYSTEM_MENU_TYPE_GET_ID)
  ResponseData<SysMenuTypeDto> getById(@PathVariable(value = "id") String id);

  /**
   * 修改状态
   * 
   * @param id     编号
   * @param status 状态
   * @return ResponseData<Boolean>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_MENU_TYPE_STATUS)
  ResponseData<Boolean> editStatus(@PathVariable(value = "id") String id, @RequestParam("status") StatusEnum status);
  
  /**
   * 判断是否存在
   *
   * @param checkCodeVo 查询参数
   * @return ResponseData<Boolean>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_MENU_TYPE_CHECK)
  ResponseData<Boolean> checkCode(@RequestBody CheckCodeVo checkCodeVo);

  /**
   * 保存
   * 
   * @param sysMenuTypeVo 保存对象
   * @return ResponseData<SysMenuTypeDto>
   */
  @PutMapping(PathConstants.REQUEST_SYSTEM_MENU_TYPE_SAVE)
  ResponseData<SysMenuTypeDto> save(@RequestBody SysMenuTypeVo sysMenuTypeVo);

  /**
   * 删除
   * 
   * @param ids 编号
   * @return ResponseData<List<Boolean>>
   */
  @DeleteMapping(PathConstants.REQUEST_SYSTEM_MENU_TYPE_DELETE)
  ResponseData<List<Boolean>> delete(@RequestBody String... ids);

}
