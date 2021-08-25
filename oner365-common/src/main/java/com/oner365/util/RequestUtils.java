/*
 *    Copyright 2016 10gen Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.oner365.util;

import javax.servlet.http.HttpServletRequest;

import com.oner365.common.auth.AuthUser;

/**
 * JavaWebToken常用类
 * 
 * @author zhaoyong
 */
public class RequestUtils {

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
     * 
     * @param request HttpServletRequest
     */
    public static void setHttpRequest(HttpServletRequest request) {
        LOCAL.set(request);
    }

    /**
     * get method
     * 
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
     *
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
     *
     * @return token字符串
     */
    public static String getToken() {
        if (getHttpRequest() != null && getHttpRequest().getAttribute(ACCESS_TOKEN) != null) {
            return getHttpRequest().getAttribute(ACCESS_TOKEN).toString();
        }
        return null;
    }

}
