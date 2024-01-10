package com.oner365.data.commons.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.util.Assert;

/**
 * 加密方式工具类
 * 
 * @author zhaoyong
 *
 */
@SuppressWarnings("deprecation")
public class PasswordEncoderUtils {

  /**
   * 加密方式枚举
   * 
   * @author zhaoyong
   *
   */
  public enum Encoder {
    /** 加密类型 */
    BCRYPT, LDAP, MD4, MD5, NOOP, PBKDF2, SCRYPT, SHA1, SHA256, SHA, ARGON2
  }

  private PasswordEncoderUtils() {
  }

  /**
   * 加密方式
   * 
   * @param encoding 加密方式枚举
   * @return PasswordEncoder
   */
  public static PasswordEncoder getDelegatingPasswordEncoder(PasswordEncoderUtils.Encoder encoding) {
    Map<String, PasswordEncoder> encoders = new HashMap<>(12);
    
    // This PasswordEncoder is not secure. Instead use an adaptive one way
    encoders.put(Encoder.LDAP.name(), new LdapShaPasswordEncoder());
    encoders.put(Encoder.MD4.name(), new Md4PasswordEncoder());
    encoders.put(Encoder.MD5.name(), new MessageDigestPasswordEncoder("MD5"));
    encoders.put(Encoder.NOOP.name(), NoOpPasswordEncoder.getInstance());
    encoders.put(Encoder.SHA1.name(), new MessageDigestPasswordEncoder("SHA-1"));
    encoders.put(Encoder.SHA256.name(), new MessageDigestPasswordEncoder("SHA-256"));
    encoders.put(Encoder.SHA.name(), new StandardPasswordEncoder());
    
    encoders.put(Encoder.BCRYPT.name(), new BCryptPasswordEncoder());
    encoders.put(Encoder.PBKDF2.name(), Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
    encoders.put(Encoder.SCRYPT.name(), SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
    encoders.put(Encoder.ARGON2.name(), Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());

    Assert.isTrue(encoders.containsKey(encoding.name()), encoding + " is not found in idToPasswordEncoder");

    DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder(encoding.name(), encoders);
    delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(encoders.get(encoding.name()));
    return delegatingPasswordEncoder;
  }
}
