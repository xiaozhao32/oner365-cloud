package com.oner365.sys.controller.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.common.auth.AuthUser;
import com.oner365.common.auth.annotation.CurrentUser;
import com.oner365.common.enums.ErrorInfoEnum;
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
 * ????????????
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

  @Resource
  private IFileServiceClient fileServiceClient;

  /**
   * ????????????
   *
   * @param data ????????????
   * @return PageInfo<SysUserDto>
   */
  @PostMapping("/list")
  public PageInfo<SysUserDto> pageList(@RequestBody QueryCriteriaBean data) {
    return sysUserService.pageList(data);
  }

  /**
   * ????????????
   *
   * @param id ??????
   * @return ResponseData<SysUserInfoVo>
   */
  @GetMapping("/get/{id}")
  public ResponseData<SysUserInfoVo> get(@PathVariable String id) {
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

    return ResponseData.success(result);
  }

  /**
   * ????????????
   * 
   * @param authUser ????????????
   * @return SysUser
   */
  @GetMapping("/profile")
  public SysUserDto profile(@CurrentUser AuthUser authUser) {
    return sysUserService.getById(authUser.getId());
  }

  /**
   * ????????????
   * 
   * @param authUser ????????????
   * @param file     ??????
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
   * ??????????????????
   * 
   * @param sysUserVo ??????
   * @param authUser  ????????????
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
   * ????????????????????????
   *
   * @param checkUserNameVo ????????????
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
   * ????????????
   *
   * @param resetPasswordVo ????????????
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
   * ????????????
   *
   * @param authUser         ????????????
   * @param modifyPasswordVo ????????????
   * @return Boolean
   */
  @PostMapping("/update/password")
  public ResponseResult<Boolean> editPassword(@CurrentUser AuthUser authUser,
      @Validated @RequestBody ModifyPasswordVo modifyPasswordVo) {
    if (modifyPasswordVo != null) {
      String oldPassword = DigestUtils.md5Hex(modifyPasswordVo.getOldPassword()).toUpperCase();
      SysUserDto sysUser = sysUserService.getById(authUser.getId());

      if (!oldPassword.equals(sysUser.getPassword())) {
        return ResponseResult.error(ErrorInfoEnum.PASSWORD_ERROR.getName());
      }
      Boolean result = sysUserService.editPassword(authUser.getId(), modifyPasswordVo.getPassword());
      return ResponseResult.success(result);
    }
    return ResponseResult.error(ErrorInfoEnum.PARAM.getName());
  }

  /**
   * ??????????????????
   *
   * @param id     ??????
   * @param status ??????
   * @return Boolean
   */
  @PostMapping("/status/{id}")
  public Boolean editStatus(@PathVariable String id, @RequestParam("status") StatusEnum status) {
    return sysUserService.editStatus(id, status);
  }

  /**
   * ????????????
   *
   * @param sysUserVo ????????????
   * @return ResponseResult<SysUserDto>
   */
  @PutMapping("/save")
  public ResponseResult<SysUserDto> save(@Validated @RequestBody SysUserVo sysUserVo) {
    if (sysUserVo != null) {
      sysUserVo.setLastIp(DataUtils.getIpAddress(RequestUtils.getHttpRequest()));
      SysUserDto entity = sysUserService.saveUser(sysUserVo);
      return ResponseResult.success(entity);
    }
    return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }

  /**
   * ????????????
   *
   * @param ids ??????
   * @return List<Boolean>
   */
  @DeleteMapping("/delete")
  public List<Boolean> delete(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> sysUserService.deleteById(id)).collect(Collectors.toList());
  }

  /**
   * ??????Excel
   * 
   * @param data ??????
   * @return ResponseEntity<byte[]>
   */
  @PostMapping("/export")
  public ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data) {
    List<SysUserDto> list = sysUserService.findList(data);

    String[] titleKeys = new String[] { "??????", "????????????", "????????????", "??????", "??????", "??????", "??????", "??????", "??????", "????????????", "??????????????????",
        "????????????ip" };
    String[] columnNames = { "id", "userCode", "userName", "realName", "sex", "email", "phone", "remark", "status",
        "createTime", "lastTime", "lastIp" };

    String fileName = SysUserDto.class.getSimpleName() + System.currentTimeMillis();
    return exportExcel(fileName, titleKeys, columnNames, list);
  }

}
