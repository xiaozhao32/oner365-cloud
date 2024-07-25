package com.oner365.data.commons.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * JavaWebToken常用类
 *
 * @author liutao
 */
public class JwtTools {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTools.class);

    private static final String PARAM_AUTH = "auth0";
    private static final String PARAM_BODY = "body";
    
    private JwtTools() {
    }

    /**
     * 生成加密后的token
     *
     * @param body   加密内容
     * @param time   时效
     * @param secret 签名key
     * @return 加密后的token
     */
    public static String getToken(String body, Date time, String secret) {
        try {
            return JWT.create().withIssuer(PARAM_AUTH).withClaim(PARAM_BODY, body)
                    .withExpiresAt(time)
                    .sign(Algorithm.HMAC256(secret));
        } catch (JWTCreationException e) {
            LOGGER.error("getToken JWTCreationException:", e);
        } catch (IllegalArgumentException e) {
            LOGGER.error("getToken IllegalArgumentException:", e);
        }
        return null;
    }

    /**
     * 解密
     * 先验证token是否被伪造，然后解码token。
     *
     * @param token  字符串token
     * @param secret 签名key
     * @return 解密后的DecodedJWT对象，可以读取token中的数据。
     */
    public static DecodedJWT decodeToken(final String token, String secret) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).withIssuer(PARAM_AUTH).build();
            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            LOGGER.error("decodeToken JWTCreationException:", e);
        } catch (IllegalArgumentException e) {
            LOGGER.error("decodeToken IllegalArgumentException:", e);
        }
        return null;
    }

}
