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
package com.oner365.gateway.jwt;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * JavaWebToken常用类
 *
 * @author zhaoyong
 */
public class JwtUtils {

    private JwtUtils() {

    }

    /**
     * 通过Token获取token中的用户名
     *
     * @param token  令牌
     * @param secret 秘钥
     * @return String
     */
    public static String getUsernameFromToken(String token, String secret) {
        String username = null;
        final Claims claims = getClaimsFromToken(token, secret);
        if (claims != null) {
            username = claims.getSubject();
        }
        return username;
    }

    /**
     * 从token中读取Claims对象
     *
     * @param token  令牌
     * @param secret 加密秘钥
     * @return Claims
     */
    public static Claims getClaimsFromToken(String token, String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * 验证token有效性
     * 
     * @param token  令牌
     * @param secret 加密秘钥
     * @return Boolean
     */
    public static Boolean validateToken(String token, String secret) {
        final String username = getUsernameFromToken(token, secret);
        if (username != null) {
            JSONObject json = JSON.parseObject(username);
            return (!Strings.isNullOrEmpty(json.getString("userName")) && !isTokenExpired(token, secret));
        }
        return false;
    }

    /**
     * 验证token是否过期
     * 
     * @param token  令牌
     * @param secret 加密秘钥
     * @return Boolean
     */
    public static Boolean isTokenExpired(String token, String secret) {
        final Date expiration = getExpirationDateFromToken(token, secret);
        if (expiration != null) {
            return expiration.before(new Date());
        }
        return Boolean.TRUE;
    }

    /**
     * 在access_token中获取有效期
     *
     * @param token  令牌
     * @param secret 秘钥
     * @return Date
     */
    public static Date getExpirationDateFromToken(String token, String secret) {
        Date expiration = null;
        final Claims claims = getClaimsFromToken(token, secret);
        if (claims != null) {
            expiration = claims.getExpiration();
        }
        return expiration;
    }

}
