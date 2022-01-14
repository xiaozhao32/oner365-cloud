package com.oner365.sys.controller.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.common.auth.AuthUser;
import com.oner365.common.auth.annotation.CurrentUser;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.AttributeBean;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.client.IFileServiceClient;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dto.SysUserDto;
import com.oner365.sys.service.ISysJobService;
import com.oner365.sys.service.ISysRoleService;
import com.oner365.sys.service.ISysUserService;
import com.oner365.sys.vo.ModifyPasswordVo;
import com.oner365.sys.vo.ResetPasswordVo;
import com.oner365.sys.vo.SysUserInfoVo;
import com.oner365.sys.vo.SysUserVo;
import com.oner365.sys.vo.check.CheckUserNameVo;
import com.oner365.util.DataUtils;
import com.oner365.util.RequestUtils;

/**
 * 用户管理
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/user")
public class SysUserController extends BaseController {

  @Autowired
  private ISysUserService sysUserService;

  @Autowired
  private ISysRoleService sysRoleService;

  @Autowired
  private ISysJobService sysJobService;

  @Autowired
  private IFileServiceClient fileServiceClient;

  /**
   * 用户列表
   *
   * @param data 查询参数
   * @return PageInfo<SysUserDto>
   */
  @PostMapping("/list")
  public PageInfo<SysUserDto> pageList(@RequestBody QueryCriteriaBean data) {
    return sysUserService.pageList(data);
  }

  /**
   * 获取信息
   *
   * @param id 编号
   * @return ResponseData<SysUserInfoVo>
   */
  @GetMapping("/get/{id}")
  public ResponseData<SysUserInfoVo> get(@PathVariable String id) {
    SysUserDto sysUser = sysUserService.getById(id);

    SysUserInfoVo result = new SysUserInfoVo();
    result.setSysUser(sysUser);

    QueryCriteriaBean data = new QueryCriteriaBean();
    List<AttributeBean> whereList = new ArrayList<>();
    AttributeBean attribute = new AttributeBean(SysConstants.STATUS, StatusEnum.YES.getCode());
    whereList.add(attribute);
    data.setWhereList(whereList);
    result.setRoleList(sysRoleService.findList(data));
    result.setJobList(sysJobService.findList(data));

    return ResponseData.success(result);
  }

  /**
   * 个人信息
   * 
   * @param authUser 登录对象
   * @return SysUser
   */
  @GetMapping("/profile")
  public SysUserDto profile(@CurrentUser AuthUser authUser) {
    return sysUserService.getById(authUser.getId());
  }

  /**
   * 上传图片
   * 
   * @param authUser 登录对象
   * @param file     文件
   * @return String
   */
  @PostMapping("/avatar")
  public String avatar(@CurrentUser AuthUser authUser, @RequestParam("avatarfile") MultipartFile file) {
    if (!file.isEmpty()) {
      ResponseData<ResponseResult<String>> responseData = fileServiceClient.uploadFile(file, "avatar");
      if (responseData != null) {
        ResponseResult<String> result = responseData.getResult();
        if (result != null) {
          sysUserService.updateAvatar(authUser.getId(), result.getMsg());
          return result.getMsg();
        }
      }
    }
    return "";
  }

  /**
   * 更新个人信息
   * 
   * @param sysUserVo 对象
   * @param authUser  登录对象
   * @return ResponseData
   */
  @PostMapping("/update/profile")
  public SysUserDto updateUserProfile(@RequestBody SysUserVo sysUserVo, @CurrentUser AuthUser authUser) {
    if (sysUserVo != null) {
      sysUserVo.setId(authUser.getId());
      return sysUserService.updateUserProfile(sysUserVo);
    }
    return null;
  }

  /**
   * 判断用户是否存在
   *
   * @param checkUserNameVo 查询参数
   * @return Long
   */
  @PostMapping("/check")
  public Long checkUserName(@RequestBody CheckUserNameVo checkUserNameVo) {
    if (checkUserNameVo != null) {
      return sysUserService.checkUserName(checkUserNameVo.getId(), checkUserNameVo.getUserName());
    }
    return Long.valueOf(ResultEnum.ERROR.getCode());
  }

  /**
   * 重置密码
   *
   * @param resetPasswordVo 查询参数
   * @return Integer
   */
  @PostMapping("/reset")
  public Integer resetPassword(@RequestBody ResetPasswordVo resetPasswordVo) {
    if (resetPasswordVo != null) {
      return sysUserService.editPassword(resetPasswordVo.getUserId(), resetPasswordVo.getPassword());
    }
    return ResultEnum.ERROR.getCode();
  }

  /**
   * 修改密码
   *
   * @param authUser         登录对象
   * @param modifyPasswordVo 请求参数
   * @return Integer
   */
  @PostMapping("/update/password")
  public ResponseResult<Integer> editPassword(@CurrentUser AuthUser authUser,
      @RequestBody ModifyPasswordVo modifyPasswordVo) {
    if (modifyPasswordVo != null) {
      String oldPassword = DigestUtils.md5Hex(modifyPasswordVo.getOldPassword()).toUpperCase();
      SysUserDto sysUser = sysUserService.getById(authUser.getId());

      if (!oldPassword.equals(sysUser.getPassword())) {
        return ResponseResult.error(ErrorInfoEnum.PASSWORD_ERROR.getName());
      }
      int result = sysUserService.editPassword(authUser.getId(), modifyPasswordVo.getPassword());
      return ResponseResult.success(result);
    }
    return ResponseResult.error(ErrorInfoEnum.PARAM.getName());
  }

  /**
   * 修改用户状态
   *
   * @param id     主键
   * @param status 状态
   * @return Integer
   */
  @PostMapping("/status/{id}")
  public Integer editStatus(@PathVariable String id, @RequestParam("status") String status) {
    return sysUserService.editStatus(id, status);
  }

  /**
   * 用户保存
   *
   * @param sysUserVo 用户对象
   * @return ResponseResult<SysUserDto>
   */
  @PutMapping("/save")
  public ResponseResult<SysUserDto> save(@RequestBody SysUserVo sysUserVo) {
    if (sysUserVo != null) {
      sysUserVo.setLastIp(DataUtils.getIpAddress(RequestUtils.getHttpRequest()));
      SysUserDto entity = sysUserService.saveUser(sysUserVo);
      return ResponseResult.success(entity);
    }
    return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }

  /**
   * 删除用户
   *
   * @param ids 编号
   * @return Integer
   */
  @DeleteMapping("/delete")
  public Integer delete(@RequestBody String... ids) {
    int code = 0;
    for (String id : ids) {
      code = sysUserService.deleteById(id);
    }
    return code;
  }

  /**
   * 导出Excel
   * 
   * @param data 参数
   * @return ResponseEntity<byte[]>
   */
  @PostMapping("/export")
  public ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data) {
    List<SysUserDto> list = sysUserService.findList(data);

    String[] titleKeys = new String[] { "编号", "用户标识", "用户名称", "姓名", "性别", "邮箱", "电话", "备注", "状态", "创建时间", "最后登录时间",
        "最后登录ip" };
    String[] columnNames = { "id", "userCode", "userName", "realName", "sex", "email", "phone", "remark", "status",
        "createTime", "lastTime", "lastIp" };

    String fileName = SysUserDto.class.getSimpleName() + System.currentTimeMillis();
    return exportExcel(fileName, titleKeys, columnNames, list);
  }

}
