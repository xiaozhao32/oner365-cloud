package com.oner365.data.web.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oner365.data.commons.auth.AuthUser;
import com.oner365.data.commons.constants.PublicConstants;

/**
 * JavaWebToken常用类
 *
 * @author zhaoyong
 */
public class RequestUtils {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestUtils.class);

    private static final ThreadLocal<HttpServletRequest> LOCAL = new ThreadLocal<>();

    public static final String AUTH_USER = "authUser";

    public static final String ACCESS_TOKEN = "accessToken";

    public static final String ERROR_TOKEN_MESSAGE = "accessToken is null";

    public static final String ERROR_AUTH_USER_MESSAGE = "authUser is null";

    public static final String TOKEN_TYPE = "tokenType";

    public static final String EXPIRED = "Expired";

    private RequestUtils() {

    }

    /**
     * set method
     * @param request HttpServletRequest
     */
    public static void setHttpRequest(HttpServletRequest request) {
        LOCAL.set(request);
    }

    /**
     * get method
     * @return HttpServletRequest
     */
    public static HttpServletRequest getHttpRequest() {
        return LOCAL.get();
    }

    /**
     * remove method
     */
    public static void remove() {
        LOCAL.remove();
    }

    /**
     * 获取用户对象
     * @return AuthUser
     */
    public static AuthUser getAuthUser() {
        if (getHttpRequest() != null && getHttpRequest().getAttribute(AUTH_USER) != null) {
            return (AuthUser) getHttpRequest().getAttribute(AUTH_USER);
        }
        return null;
    }

    /**
     * 获取token
     * @return token字符串
     */
    public static String getToken() {
        if (getHttpRequest() != null && getHttpRequest().getAttribute(ACCESS_TOKEN) != null) {
            return getHttpRequest().getAttribute(ACCESS_TOKEN).toString();
        }
        return null;
    }
    
    /**
     * 验证白名单
     * @param uri 请求地址
     * @param paths 白名单
     * @return boolean
     */
    public static boolean validateClientWhites(String uri, List<String> paths) {
        if (PublicConstants.DELIMITER.equals(uri)) {
            return true;
        }
        return paths.stream().anyMatch(uri::contains);
    }
    
    /**
     * 获取RequestBody
     * @param stream 输入流
     * @return String
     */
    public static String getRequestBody(InputStream stream) {
        String line;
        StringBuilder body = new StringBuilder(PublicConstants.BYTE_SIZE);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            while ((line = reader.readLine()) != null) {
                body.append(line.trim());
            }
        }
        catch (IOException e) {
            LOGGER.error("request body error:", e);
        }
        return body.toString();
    }

}
