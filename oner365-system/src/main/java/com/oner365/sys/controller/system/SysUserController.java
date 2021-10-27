package com.oner365.sys.controller.system;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;
import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.common.auth.AuthUser;
import com.oner365.common.auth.annotation.CurrentUser;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.query.AttributeBean;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.client.IFileServiceClient;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.entity.SysUser;
import com.oner365.sys.service.ISysJobService;
import com.oner365.sys.service.ISysRoleService;
import com.oner365.sys.service.ISysUserService;
import com.oner365.sys.vo.ModifyPasswordVo;
import com.oner365.sys.vo.ResetPasswordVo;
import com.oner365.sys.vo.SysUserVo;
import com.oner365.sys.vo.check.CheckUserNameVo;
import com.oner365.util.DataUtils;

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
    private IFileServiceClient fastdfsClient;

    /**
     * 用户列表
     *
     * @param data 查询参数
     * @return Page<SysUser>
     */
    @PostMapping("/list")
    public Page<SysUser> pageList(@RequestBody QueryCriteriaBean data) {
        return sysUserService.pageList(data);
    }

    /**
     * 用户保存
     *
     * @param sysUserVo 用户对象
     * @return ResponseResult<SysUser>
     */
    @PutMapping("/save")
    public ResponseResult<SysUser> save(@RequestBody SysUserVo sysUserVo, HttpServletRequest request) {
        if (sysUserVo != null) {
            SysUser sysUser = sysUserVo.toObject();
            if (sysUser != null) {
            sysUser.setLastIp(DataUtils.getIpAddress(request));
            SysUser entity = sysUserService.saveUser(sysUser);
                return ResponseResult.success(entity);
            }
        }
        return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
    }

    /**
     * 获取信息
     *
     * @param id 编号
     * @return Map<String, Object>
     */
    @GetMapping("/get/{id}")
    public ResponseData<Map<String, Object>> get(@PathVariable String id) {
        SysUser sysUser = sysUserService.getById(id);

        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.MSG, sysUser);

        QueryCriteriaBean data = new QueryCriteriaBean();
        List<AttributeBean> whereList = new ArrayList<>();
        AttributeBean attribute = new AttributeBean(SysConstants.STATUS, StatusEnum.YES.getOrdinal());
        whereList.add(attribute);
        data.setWhereList(whereList);
        result.put("roleList", sysRoleService.findList(data));
        result.put("jobList", sysJobService.findList(data));
        
        return ResponseData.success(result);
    }

    /**
     * 个人信息
     * 
     * @param authUser 登录对象
     * @return SysUser
     */
    @GetMapping("/profile")
    public SysUser profile(@CurrentUser AuthUser authUser) {
        return sysUserService.getById(authUser.getId());
    }

    /**
     * 上传图片
     * 
     * @param authUser 登录对象
     * @param file     文件
     * @return ResponseData<Map<String, Object>>
     */
    @PostMapping("/avatar")
    public ResponseData<Map<String, Object>> avatar(@CurrentUser AuthUser authUser, @RequestParam("avatarfile") MultipartFile file) {
        if (!file.isEmpty()) {
            ResponseData<Map<String, Object>> responseData = fastdfsClient.upload(file);
            if (responseData.getCode() == ResultEnum.SUCCESS.getOrdinal()) {
                Map<String, Object> data = responseData.getResult();

                SysUser sysUser = sysUserService.getById(authUser.getId());
                sysUser.setAvatar(data.get(PublicConstants.MSG).toString());
                sysUserService.update(sysUser);
            }
            return responseData;
        }
        return new ResponseData<>(ResultEnum.ERROR.getOrdinal(), ErrorInfoEnum.PARAM.getName());
    }

    /**
     * 更新个人信息
     * 
     * @param sysUserVo 对象
     * @param authUser  登录对象
     * @return ResponseData
     */
    @PostMapping("/updateUserProfile")
    public SysUser updateUserProfile(@RequestBody SysUserVo sysUserVo, @CurrentUser AuthUser authUser) {
        SysUser vo = sysUserVo.toObject();
        if (vo != null) {
            SysUser sysUser = sysUserService.getById(authUser.getId());
            sysUser.setEmail(vo.getEmail());
            sysUser.setRealName(vo.getRealName());
            sysUser.setPhone(vo.getPhone());
            sysUser.setSex(vo.getSex());
            sysUser.setLastTime(LocalDateTime.now());
            return sysUserService.update(sysUser);
        }
        return null;
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
     * 判断用户是否存在
     *
     * @param checkUserNameVo 查询参数
     * @return Long
     */
    @PostMapping("/checkUserName")
    public Long checkUserName(@RequestBody CheckUserNameVo checkUserNameVo) {
        if (checkUserNameVo != null) {
            return sysUserService.checkUserName(checkUserNameVo.getId(), checkUserNameVo.getUserName());
        }
        return Long.valueOf(ResultEnum.ERROR.getOrdinal());
    }

    /**
     * 修改密码
     *
     * @param resetPasswordVo 查询参数
     * @return Integer
     */
    @PostMapping("/resetPassword")
    public Integer resetPassword(@RequestBody ResetPasswordVo resetPasswordVo) {
        if (resetPasswordVo != null) {
            return sysUserService.editPassword(resetPasswordVo.getUserId(), resetPasswordVo.getPassword());
        }
        return ResultEnum.ERROR.getOrdinal();
    }

    /**
     * 修改密码
     *
     * @param authUser         登录对象
     * @param modifyPasswordVo 请求参数
     * @return Integer
     */
    @PostMapping("/editPassword")
    public ResponseResult<Integer> editPassword(@CurrentUser AuthUser authUser, @RequestBody ModifyPasswordVo modifyPasswordVo) {
        if (modifyPasswordVo != null) {
            String oldPassword = DigestUtils.md5Hex(modifyPasswordVo.getOldPassword()).toUpperCase();
            SysUser sysUser = sysUserService.getById(authUser.getId());

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
    @PostMapping("/editStatus/{id}")
    public Integer editStatus(@PathVariable String id, @RequestParam("status") String status) {
        return sysUserService.editStatus(id, status);
    }

    /**
     * 导出Excel
     * 
     * @param data 参数
     * @return ResponseEntity<byte[]>
     */
    @PostMapping("/export")
    public ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data) {
        List<SysUser> list = sysUserService.findList(data);

        String[] titleKeys = new String[] { "编号", "用户标识", "用户名称", "姓名", "性别", "邮箱", "电话", "备注", "状态", "创建时间", "最后登录时间",
                "最后登录ip" };
        String[] columnNames = { "id", "userCode", "userName", "realName", "sex", "email", "phone", "remark", "status",
                "createTime", "lastTime", "lastIp" };

        String fileName = SysUser.class.getSimpleName() + System.currentTimeMillis();
        return exportExcel(fileName, titleKeys, columnNames, list);
    }

}
