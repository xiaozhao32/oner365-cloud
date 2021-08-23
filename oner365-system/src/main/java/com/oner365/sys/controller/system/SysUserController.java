package com.oner365.sys.controller.system;

import java.sql.Timestamp;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oner365.common.ResponseData;
import com.oner365.common.auth.AuthUser;
import com.oner365.common.auth.annotation.CurrentUser;
import com.oner365.common.constants.ErrorCodes;
import com.oner365.common.constants.ErrorInfo;
import com.oner365.common.constants.PublicConstants;
import com.oner365.controller.BaseController;
import com.oner365.sys.client.IFileServiceClient;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.entity.SysUser;
import com.oner365.sys.service.ISysJobService;
import com.oner365.sys.service.ISysRoleService;
import com.oner365.sys.service.ISysUserService;
import com.oner365.util.DataUtils;
import com.google.common.collect.Maps;

/**
 * 用户管理
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
     * @param paramJson 参数
     * @return Page<SysUser>
     */
    @PostMapping("/list")
    public Page<SysUser> findUserList(@RequestBody JSONObject paramJson) {
        return sysUserService.pageList(paramJson);
    }

    /**
     * 用户保存
     *
     * @param paramJson 用户对象
     * @return Map<String, Object>
     */
    @PutMapping("/save")
    public Map<String, Object> save(@RequestBody JSONObject paramJson, HttpServletRequest request) {
        SysUser sysUser = JSON.toJavaObject(paramJson, SysUser.class);
        
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, PublicConstants.ERROR_CODE);
        if (sysUser != null) {
            sysUser.setLastIp(DataUtils.getIpAddress(request));
            SysUser entity = sysUserService.saveUser(sysUser);
            result.put(PublicConstants.CODE, PublicConstants.SUCCESS_CODE);
            result.put(PublicConstants.MSG, entity);
        }
        return result;
    }

    /**
     * 获取信息
     *
     * @param id 编号
     * @return Map<String, Object>
     */
    @GetMapping("/get/{id}")
    public Map<String, Object> get(@PathVariable String id) {
        SysUser sysUser = sysUserService.getById(id);

        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.MSG, sysUser);

        result.put("roleList", sysRoleService.findList(new JSONObject()));
        result.put("jobList", sysJobService.findList(new JSONObject()));
        return result;
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
     * @return ResponseData
     */
    @PostMapping("avatar")
    public ResponseData<Map<String, Object>> avatar(@CurrentUser AuthUser authUser, @RequestParam("avatarfile") MultipartFile file) {
        if (!file.isEmpty()) {
            ResponseData<Map<String, Object>> responseData = fileServiceClient.upload(file);
            if (responseData.getCode() == PublicConstants.SUCCESS_CODE) {
                Map<String, Object> data = responseData.getResult();

                SysUser sysUser = sysUserService.getById(authUser.getId());
                sysUser.setAvatar(data.get(PublicConstants.MSG).toString());
                sysUserService.update(sysUser);
            }
            return responseData;
        }
        return new ResponseData<>(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
    }

    /**
     * 更新个人信息
     * 
     * @param paramJson 对象
     * @param authUser  登录对象
     * @return SysUser
     */
    @PostMapping("/updateUserProfile")
    public SysUser updateUserProfile(@RequestBody JSONObject paramJson, @CurrentUser AuthUser authUser) {
        SysUser vo = JSON.toJavaObject(paramJson, SysUser.class);
        if (vo != null) {
            SysUser sysUser = sysUserService.getById(authUser.getId());
            sysUser.setEmail(vo.getEmail());
            sysUser.setRealName(vo.getRealName());
            sysUser.setPhone(vo.getPhone());
            sysUser.setSex(vo.getSex());
            sysUser.setLastTime(new Timestamp(System.currentTimeMillis()));
            return sysUserService.update(sysUser);
        }
        return null;
    }

    /**
     * 删除用户
     *
     * @param ids 编号
     * @return Map<String, Object>
     */
    @DeleteMapping("/delete")
    public Map<String, Object> delete(@RequestBody String... ids) {
        int code = 0;
        for (String id : ids) {
            code = sysUserService.deleteById(id);
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, code);
        return result;
    }

    /**
     * 判断用户是否存在
     *
     * @param json 参数
     * @return Map<String, Object>
     */
    @PostMapping("/checkUserName")
    public Map<String, Object> checkUserName(@RequestBody JSONObject json) {
        String userName = json.getString(SysConstants.USER_NAME);
        String userId = json.getString(SysConstants.ID);
        long code = sysUserService.checkUserName(userId, userName);

        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, code);
        return result;
    }

    /**
     * 修改密码
     *
     * @param json 参数
     * @return Map<String, Object>
     */
    @PostMapping("/resetPassword")
    public Map<String, Object> resetPassword(@RequestBody JSONObject json) {
        String userId = json.getString(SysConstants.USER_ID);
        String password = json.getString(SysConstants.P);
        Integer code = sysUserService.editPassword(userId, password);

        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, code);
        return result;
    }

    /**
     * 修改密码
     *
     * @param authUser 登录对象
     * @param json     参数
     * @return ResponseData
     */
    @PostMapping("/editPassword")
    public ResponseData<Map<String, Object>> editPassword(@CurrentUser AuthUser authUser, @RequestBody JSONObject json) {
        String oldPassword = DigestUtils.md5Hex(json.getString("oldPassword")).toUpperCase();
        String password = json.getString(SysConstants.P);
        SysUser sysUser = sysUserService.getById(authUser.getId());
        if (!oldPassword.equals(sysUser.getPassword())) {
            return new ResponseData<>(ErrorCodes.ERR_PASSWORD_ERROR, ErrorInfo.ERR_PASS_ERROR);
        }
        Integer code = sysUserService.editPassword(authUser.getId(), password);

        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, code);
        return new ResponseData<>(result);
    }

    /**
     * 修改用户状态
     *
     * @param json 参数
     * @return Map<String, Object>
     */
    @PostMapping("/editStatus")
    public Map<String, Object> editStatus(@RequestBody JSONObject json) {
        String status = json.getString(SysConstants.STATUS);
        String userId = json.getString(SysConstants.USER_ID);
        Integer code = sysUserService.editStatus(userId, status);

        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, code);
        return result;
    }

    /**
     * 导出Excel
     * 
     * @param paramJson 参数
     * @return ResponseData
     */
    @PostMapping("/export")
    public ResponseEntity<byte[]> export(@RequestBody JSONObject paramJson) {
        List<SysUser> list = sysUserService.findList(paramJson);

        String[] titleKeys = new String[] { "编号", "用户标识", "用户名称", "姓名", "性别", "邮箱", "电话", "备注", "状态", "创建时间", "最后登录时间",
                "最后登录ip" };
        String[] columnNames = { "id", "userCode", "userName", "realName", "sex", "email", "phone", "remark", "status",
                "createTime", "lastTime", "lastIp" };

        String fileName = SysUser.class.getSimpleName() + System.currentTimeMillis();
        return exportExcel(fileName, titleKeys, columnNames, list);
    }

}
