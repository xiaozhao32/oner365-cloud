package com.oner365.sys.controller.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import com.oner365.data.commons.auth.AuthUser;
import com.oner365.data.commons.auth.annotation.CurrentUser;
import com.oner365.data.commons.enums.ErrorInfoEnum;
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.util.Md5Util;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.AttributeBean;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.web.controller.BaseController;
import com.oner365.data.web.utils.HttpClientUtils;
import com.oner365.data.web.utils.RequestUtils;
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

/**
 * 用户管理
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/user")
public class SysUserController extends BaseController {

  @Resource
  private ISysUserService sysUserService;

  @Resource
  private ISysRoleService sysRoleService;

  @Resource
  private ISysJobService sysJobService;

  @Resource
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
   * @return SysUserInfoVo
   */
  @GetMapping("/get/{id}")
  public SysUserInfoVo get(@PathVariable String id) {
    SysUserDto sysUser = sysUserService.getById(id);

    SysUserInfoVo result = new SysUserInfoVo();
    result.setSysUser(sysUser);

    QueryCriteriaBean data = new QueryCriteriaBean();
    List<AttributeBean> whereList = new ArrayList<>();
    AttributeBean attribute = new AttributeBean(SysConstants.STATUS, StatusEnum.YES);
    whereList.add(attribute);
    data.setWhereList(whereList);
    result.setRoleList(sysRoleService.findList(data));
    result.setJobList(sysJobService.findList(data));

    return result;
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
      String result = fileServiceClient.uploadFile(file, "avatar");
      if (result != null) {
        SysUserDto sysUserDto = sysUserService.updateAvatar(authUser.getId(), result);
        return sysUserDto.getAvatar();
      }
    }
    return null;
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
    sysUserVo.setId(authUser.getId());
    return sysUserService.updateUserProfile(sysUserVo);
  }

  /**
   * 判断用户是否存在
   *
   * @param checkUserNameVo 查询参数
   * @return Boolean
   */
  @PostMapping("/check")
  public Boolean checkUserName(@Validated @RequestBody CheckUserNameVo checkUserNameVo) {
    if (checkUserNameVo != null) {
      return sysUserService.checkUserName(checkUserNameVo.getId(), checkUserNameVo.getUserName());
    }
    return Boolean.FALSE;
  }

  /**
   * 重置密码
   *
   * @param resetPasswordVo 查询参数
   * @return Integer
   */
  @PostMapping("/reset")
  public Boolean resetPassword(@Validated @RequestBody ResetPasswordVo resetPasswordVo) {
    if (resetPasswordVo != null) {
      return sysUserService.editPassword(resetPasswordVo.getUserId(), resetPasswordVo.getPassword());
    }
    return Boolean.FALSE;
  }

  /**
   * 修改密码
   *
   * @param authUser         登录对象
   * @param modifyPasswordVo 请求参数
   * @return String
   */
  @PostMapping("/update/password")
  public String editPassword(@CurrentUser AuthUser authUser,
      @Validated @RequestBody ModifyPasswordVo modifyPasswordVo) {
    String oldPassword = Md5Util.getInstance().getMd5(modifyPasswordVo.getOldPassword()).toUpperCase();
    SysUserDto sysUser = sysUserService.getById(authUser.getId());

    if (!oldPassword.equals(sysUser.getPassword())) {
      return ErrorInfoEnum.PASSWORD_ERROR.getName();
    }
    Boolean result = sysUserService.editPassword(authUser.getId(), modifyPasswordVo.getPassword());
    return String.valueOf(result);
  }

  /**
   * 修改用户状态
   *
   * @param id     主键
   * @param status 状态
   * @return Boolean
   */
  @PostMapping("/status/{id}")
  public Boolean editStatus(@PathVariable String id, @RequestParam StatusEnum status) {
    return sysUserService.editStatus(id, status);
  }

  /**
   * 用户保存
   *
   * @param sysUserVo 用户对象
   * @return SysUserDto
   */
  @PutMapping("/save")
  public SysUserDto save(@Validated @RequestBody SysUserVo sysUserVo) {
    sysUserVo.setLastIp(HttpClientUtils.getIpAddress(RequestUtils.getHttpRequest()));
    return sysUserService.saveUser(sysUserVo);
  }

  /**
   * 删除用户
   *
   * @param ids 编号
   * @return List<Boolean>
   */
  @DeleteMapping("/delete")
  public List<Boolean> delete(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> sysUserService.deleteById(id)).collect(Collectors.toList());
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
