package com.oner365.data.commons.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JavaWebToken常用类
 *
 * @author zhaoyong
 */
public class JwtUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

  /**
   * Token 解析中的参数
   */
  private static final String TOKEN_USER_NAME = "userName";

  private JwtUtils() {

  }
  
  /**
   * 创建token
   *
   * @param username 账号
   * @param expired  过期时间
   * @param secret   密钥
   * @return String
   */
  public static String generateToken(String username, Date expired, String secret) {
    Map<String, Object> claims = new HashMap<>(2);
    claims.put("sub", RsaUtils.encrypt(username));
    claims.put("created", DateUtil.getDate());
    return Jwts.builder().setClaims(claims).setExpiration(expired).signWith(SignatureAlgorithm.HS512, secret).compact();
  }

  /**
   * 通过Token获取token中的用户名
   *
   * @param token  令牌
   * @param secret 秘钥
   * @return String
   */
  public static String getUsernameFromToken(String token, String secret) {
    if (DataUtils.isEmpty(token)) {
      return null;
    }
    final Claims claims = getClaimsFromToken(token, secret);
    if (claims != null && claims.getSubject() != null) {
      return RsaUtils.decrypt(claims.getSubject());
    }
    return null;
  }

  /**
   * 从token中读取Claims对象
   *
   * @param token  令牌
   * @param secret 加密秘钥
   * @return Claims
   */
  private static Claims getClaimsFromToken(String token, String secret) {
    try {
      return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e) {
      LOGGER.error("token: {}, 已过期: {}", token, e.getMessage());
      return e.getClaims();
    } catch (Exception e) {
      LOGGER.error("getClaimsFromToken error", e);
    }
    return null;
  }

  /**
   * 验证token有效性
   *
   * @param token  令牌
   * @param secret 加密秘钥
   * @return Boolean
   */
  public static Boolean validateToken(String token, String secret) {
    final String userName = getUsernameFromToken(token, secret);
    if (userName != null) {
      return (!DataUtils.isEmpty(JSON.parseObject(userName).getString(TOKEN_USER_NAME))
          && !isTokenExpired(token, secret));
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
  private static Boolean isTokenExpired(String token, String secret) {
    final Date expiration = getExpirationDateFromToken(token, secret);
    if (expiration != null) {
      return expiration.before(DateUtil.getDate());
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
