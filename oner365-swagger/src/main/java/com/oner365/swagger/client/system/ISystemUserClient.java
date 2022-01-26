package com.oner365.swagger.client.system;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.SysUserDto;
import com.oner365.swagger.vo.ModifyPasswordVo;
import com.oner365.swagger.vo.ResetPasswordVo;
import com.oner365.swagger.vo.SysUserVo;
import com.oner365.swagger.vo.check.CheckUserNameVo;

/**
 * 系统服务 - 用户管理
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_SYSTEM, contextId = PathConstants.CONTEXT_SYSTEM_USER_ID)
public interface ISystemUserClient {

  /**
   * 列表
   * 
   * @param data 查询参数
   * @return ResponseData<PageInfo<SysUserDto>>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_USER_LIST)
  ResponseData<PageInfo<SysUserDto>> list(@RequestBody QueryCriteriaBean data);

  /**
   * 按id获取查询
   * 
   * @param id 编号
   * @return ResponseData<SysUserDto>
   */
  @GetMapping(PathConstants.REQUEST_SYSTEM_USER_GET_ID)
  ResponseData<SysUserDto> getById(@PathVariable(value = "id") String id);
  
  /**
   * 获取个人信息
   * 
   * @return ResponseData<SysUserDto>
   */
  @GetMapping(PathConstants.REQUEST_SYSTEM_USER_PROFILE)
  ResponseData<SysUserDto> profile();
  
  /**
   * 上传图片
   *
   * @param file     文件
   * @return ResponseData<String>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_USER_AVATAR)
  ResponseData<String> avatar(@RequestParam("avatarfile") MultipartFile file);
  
  /**
   * 更新个人信息
   * 
   * @param sysUserVo 对象
   * @return ResponseData<SysUserDto>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_USER_PROFILE_UPDATE)
  ResponseData<SysUserDto> updateUserProfile(@RequestBody SysUserVo sysUserVo);
  
  /**
   * 判断用户是否存在
   *
   * @param checkUserNameVo 查询参数
   * @return ResponseData<Long>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_USER_CHECK)
  ResponseData<Long> checkUserName(@RequestBody CheckUserNameVo checkUserNameVo);
  
  /**
   * 重置密码
   * 
   * @param resetPasswordVo 重置对象
   * @return ResponseData<Integer>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_USER_RESET)
  ResponseData<Integer> resetPassword(@RequestBody ResetPasswordVo resetPasswordVo);
  
  /**
   * 修改密码
   * 
   * @param modifyPasswordVo 修改对象
   * @return ResponseData<Integer>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_USER_PASSWORD_UPDATE)
  ResponseData<Integer> editPassword(@RequestBody ModifyPasswordVo modifyPasswordVo);
  
  /**
   * 修改状态
   * 
   * @param id     编号
   * @param status 状态
   * @return ResponseData<Integer>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_USER_STATUS)
  ResponseData<Integer> editStatus(@PathVariable(value = "id") String id, @RequestParam("status") String status);

  /**
   * 保存
   * 
   * @param sysUserVo 保存对象
   * @return ResponseData<ResponseResult<SysUserDto>>
   */
  @PutMapping(PathConstants.REQUEST_SYSTEM_USER_SAVE)
  ResponseData<ResponseResult<SysUserDto>> save(@RequestBody SysUserVo sysUserVo);

  /**
   * 删除
   * 
   * @param ids 编号
   * @return ResponseData<List<Integer>>
   */
  @DeleteMapping(PathConstants.REQUEST_SYSTEM_USER_DELETE)
  ResponseData<List<Integer>> delete(@RequestBody String... ids);

  /**
   * 导出
   * @param data 查询对象
   * @return ResponseEntity<byte[]>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_USER_EXPORT)
  ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data);
}
