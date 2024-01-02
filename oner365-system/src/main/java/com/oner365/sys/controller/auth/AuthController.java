package com.oner365.sys.controller.auth;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.util.Base64Utils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Producer;
import com.oner365.data.commons.auth.AuthUser;
import com.oner365.data.commons.auth.annotation.CurrentUser;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.enums.ErrorInfoEnum;
import com.oner365.data.commons.enums.ResultEnum;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.commons.reponse.ResponseResult;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.redis.RedisCache;
import com.oner365.data.redis.constants.CacheConstants;
import com.oner365.data.web.controller.BaseController;
import com.oner365.data.web.utils.HttpClientUtils;
import com.oner365.data.web.utils.RequestUtils;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dto.CaptchaImageDto;
import com.oner365.sys.dto.LoginUserDto;
import com.oner365.sys.dto.SysMenuOperDto;
import com.oner365.sys.dto.SysMenuTreeDto;
import com.oner365.sys.service.ISysRoleService;
import com.oner365.sys.service.ISysUserService;
import com.oner365.sys.vo.LoginUserVo;

/**
 * 认证登录接口
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ISysRoleService sysRoleService;

    @Resource
    private RedisCache redisCache;

    @Resource(name = "captchaProducer")
    private Producer producer;

    /**
     * 系统登录
     *
     * @param loginUserVo 登录对象
     * @return ResponseData<LoginUserDto>
     */
    @PostMapping("/login")
    public ResponseData<LoginUserDto> login(@Validated @RequestBody LoginUserVo loginUserVo) {
        // 验证码
        if (!DataUtils.isEmpty(loginUserVo.getUuid())) {
            String verifyKey = SysConstants.CAPTCHA_IMAGE + PublicConstants.COLON + loginUserVo.getUuid();
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
        String ip = HttpClientUtils.getIpAddress(RequestUtils.getHttpRequest());

        // 登录
        LoginUserDto result = sysUserService.login(userName, password, ip);

        // 返回结果
        if (result != null) {
            return ResponseData.success(result);
        }
        return ResponseData.error(ErrorInfoEnum.USER_PASSWORD_ERROR.getName());
    }

    /**
     * 获取验证码
     * 
     * @return CaptchaImageDto
     */
    @GetMapping("/captcha")
    public CaptchaImageDto captchaImage() {
        // 生成随机字串
        String verifyCode = producer.createText();
        // 唯一标识
        String uuid = UUID.randomUUID().toString();
        String verifyKey = SysConstants.CAPTCHA_IMAGE + PublicConstants.COLON + uuid;
        redisCache.setCacheObject(verifyKey, verifyCode, 3, TimeUnit.MINUTES);

        CaptchaImageDto result = new CaptchaImageDto();

        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            BufferedImage image = producer.createImage(verifyCode);
            ImageIO.write(image, "jpg", stream);

            result.setUuid(uuid);
            result.setImg(Base64Utils.encodeToString(stream.toByteArray()));
        } catch (IOException e) {
            logger.error("Error captchaImage: ", e);
        }

        return result;
    }

    /**
     * 获取左侧菜单
     *
     * @return List<SysMenuTreeDto>
     */
    @GetMapping("/menu")
    public List<SysMenuTreeDto> findMenuByRoles(@CurrentUser AuthUser user) {
        try {
            if (user != null && !user.getRoleList().isEmpty() && !DataUtils.isEmpty(user.getMenuType())) {
                return sysRoleService.findMenuByRoles(user.getRoleList(), user.getMenuType());
            }
        } catch (Exception e) {
            logger.error("Error findMenuByRoles: ", e);
        }
        return Collections.emptyList();
    }

    /**
     * 获取菜单对应权限
     *
     * @param menuId 菜单id
     * @return List<SysMenuOperDto>>
     */
    @GetMapping("/menu/operation/{menuId}")
    public List<SysMenuOperDto> findMenuOperByRoles(@CurrentUser AuthUser user, @PathVariable String menuId) {
        try {
            if (user != null && !user.getRoleList().isEmpty()) {
                return sysRoleService.findMenuOperByRoles(user.getRoleList(), menuId);
            }
        } catch (Exception e) {
            logger.error("Error findMenuOperByRoles: ", e);
        }
        return Collections.emptyList();
    }

    /**
     * 退出登录
     * 
     * @return ResponseResult<String>
     */
    @PostMapping("/logout")
    public ResponseResult<String> logout(@CurrentUser AuthUser authUser) {
        if (authUser != null) {
            String key = CacheConstants.CACHE_LOGIN_NAME + authUser.getUserName();
            redisCache.deleteObject(key);
        }
        return ResponseResult.success(ResultEnum.SUCCESS.getName());
    }

}
