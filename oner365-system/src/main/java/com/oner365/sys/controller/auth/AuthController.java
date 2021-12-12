package com.oner365.sys.controller.auth;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.oner365.common.ResponseData;
import com.oner365.common.auth.AuthUser;
import com.oner365.common.auth.annotation.CurrentUser;
import com.oner365.common.cache.RedisCache;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.controller.BaseController;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dto.LoginUserDto;
import com.oner365.sys.service.ISysRoleService;
import com.oner365.sys.service.ISysUserService;
import com.oner365.sys.vo.LoginUserVo;
import com.oner365.util.DataUtils;
import com.oner365.util.RequestUtils;
import com.oner365.util.VerifyCodeUtils;

/**
 * 认证登录接口
 * @author zhaoyong
 */
@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

    private static final String CACHE_LOGIN_NAME = "Auth:Login";
    
    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private RedisCache redisCache;
    
    /**
     * 系统登录
     *
     * @param loginUserVo 登录对象
     * @return ResponseData
     */
    @PostMapping("/login")
    public ResponseData<LoginUserDto> login(@RequestBody LoginUserVo loginUserVo) {
        // 验证码
        if (!DataUtils.isEmpty(loginUserVo.getUuid())) {
            String verifyKey = SysConstants.CAPTCHA_IMAGE + ":" + loginUserVo.getUuid();
            String captcha = redisCache.getCacheObject(verifyKey);
            redisCache.deleteObject(verifyKey);
            if (captcha == null || !captcha.equalsIgnoreCase(loginUserVo.getCode())) {
                return ResponseData.error(ErrorInfoEnum.CAPCHA_ERROR.getName());
            }
        }
        
        // 验证参数
        String userName = loginUserVo.getUserName();
        if (DataUtils.isEmpty(userName)) {
            return ResponseData.error(ErrorInfoEnum.USER_NAME_NOT_NULL.getName());
        }
        String password = loginUserVo.getPassword();
        if (DataUtils.isEmpty(password)) {
            return ResponseData.error(ErrorInfoEnum.PASSWORD_NOT_NULL.getName());
        }
        // ip地址
        String ip = DataUtils.getIpAddress(RequestUtils.getHttpRequest());
        LOGGER.info("ip: {}", ip);

        // 登录
        LoginUserDto result = sysUserService.login(userName, password);
        
        // 返回结果
        if (result != null) {
            return ResponseData.success(result);
        }
        return ResponseData.error(ErrorInfoEnum.USER_PASSWORD_ERROR.getName());
    }

    /**
     * 退出登录
     * @return String
     */
    @PostMapping("/logout")
    public ResponseData<String> logout(@CurrentUser AuthUser authUser) {
        String key = CACHE_LOGIN_NAME + ":" + authUser.getUserName();
        redisCache.deleteObject(key);
        return ResponseData.success(ResultEnum.SUCCESS.getName());
    }

    /**
     * 获取左侧菜单
     *
     * @param menuType 菜单类型
     * @return JSONArray
     */
    @GetMapping("/menu/{menuType}")
    public JSONArray findMenuByRoles(@CurrentUser AuthUser user, @PathVariable String menuType) {
        try {
            if (user != null && !user.getRoleList().isEmpty()) {
              JSONArray result = sysRoleService.findMenuByRoles(user.getRoleList(), menuType);
              return result;
            }
        } catch (Exception e) {
            LOGGER.error("Error findMenuByRoles: ", e);
        }
        return new JSONArray();
    }

    /**
     * 获取验证码
     * @return Map<String, Object>
     */
    @GetMapping("/captchaImage")
    public Map<String, Object> captchaImage() {
        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        // 唯一标识
        String uuid = UUID.randomUUID().toString();
        String verifyKey = SysConstants.CAPTCHA_IMAGE + ":" + uuid;
        redisCache.setCacheObject(verifyKey, verifyCode, 3, TimeUnit.MINUTES);

        Map<String, Object> result = new HashMap<>();
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            // 生成图片
            int w = 111;
            int h = 36;
            VerifyCodeUtils.outputImage(w, h, stream, verifyCode);

            result.put(SysConstants.UUID, uuid);
            result.put("img", Base64Utils.encodeToString(stream.toByteArray()));
        } catch (IOException e) {
            LOGGER.error("Error captchaImage: ", e);
        }

        return result;
    }

    /**
     * 获取菜单对应权限
     *
     * @param menuId 菜单id
     * @return List<Map<String, Object>>>
     */
    @GetMapping("/menu/operation/{menuId}")
    public List<Map<String, String>> findMenuOperByRoles(@CurrentUser AuthUser user, @PathVariable String menuId) {
        try {
            if (user != null && !user.getRoleList().isEmpty()) {
                return sysRoleService.findMenuOperByRoles(user.getRoleList(), menuId);
            }
        } catch (Exception e) {
            LOGGER.error("Error findMenuOperByRoles: ", e);
        }
        return Collections.emptyList();
    }

    

}
