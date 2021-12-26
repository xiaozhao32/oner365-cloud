package com.oner365.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AES加密算法工具类
 *
 * @author zhaoyong
 *
 */
public class AesUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(AesUtils.class);

    /**
     * 密钥
     */
    private static final String KEY_STR = "turPark";

    private static final int LENGTH_12 = 12;
    private static final int LENGTH_16 = 16;

    /**
     * 加密类型
     */
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final ThreadLocal<Map<String, Key>> LOCAL_MAP_KEY = new ThreadLocal<>();

    private AesUtils() {

    }

    public static void removeKey() {
        LOCAL_MAP_KEY.remove();
    }

    public static SecretKeySpec getKey(String strKey) {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(strKey.getBytes());
            generator.init(128, secureRandom);
            SecretKey secretKey = generator.generateKey();
            SecretKeySpec result = new SecretKeySpec(secretKey.getEncoded(), "AES");
            Map<String, Key> map = new HashMap<>(2);
            map.put(strKey, result);
            LOCAL_MAP_KEY.set(map);
            return result;
        } catch (Exception e) {
            LOGGER.error("初始化密钥出现异常 ", e);
        }
        return null;
    }

    /**
     * 对str进行AES加密
     *
     * @param str 字符串
     * @return String
     */
    public static String getEncryptString(String str) {
        return getEncryptString(str, KEY_STR);
    }

    /**
     * 对str进行AES解密
     *
     * @param str 字符串
     * @return String
     */
    public static String getDecryptString(String str) {
        return getDecryptString(str, KEY_STR);
    }

    public static Key getSecretKey(String saltKey) {
        Key secretKey;
        if (LOCAL_MAP_KEY.get() != null) {
            secretKey = LOCAL_MAP_KEY.get().get(saltKey);
        } else {
            secretKey = getKey(saltKey);
        }
        return secretKey;
    }

    /**
     * 对str进行AES加密
     *
     * @param str 字符串
     * @param key 键
     * @return String
     */
    public static String getEncryptString(String str, String key) {
        try {
            byte[] iv = new byte[LENGTH_12];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(iv);
            byte[] contentBytes = str.getBytes(StandardCharsets.UTF_8);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec params = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key), params);
            byte[] encryptData = cipher.doFinal(contentBytes);
            assert encryptData.length == contentBytes.length + LENGTH_16;
            byte[] message = new byte[LENGTH_12 + contentBytes.length + LENGTH_16];
            System.arraycopy(iv, 0, message, 0, LENGTH_12);
            System.arraycopy(encryptData, 0, message, LENGTH_12, encryptData.length);
            return Base64.getEncoder().encodeToString(message);
        } catch (Exception e) {
            LOGGER.error("getEncryptString error:", e);
        }
        return null;
    }

    /**
     * 对str进行AES解密
     *
     * @param str 字符串
     * @param key 键
     * @return String
     */
    public static String getDecryptString(String str, String key) {
        try {
            byte[] content = Base64.getDecoder().decode(str);
            if (content.length < LENGTH_12 + LENGTH_16) {
                throw new IllegalArgumentException();
            }
            GCMParameterSpec params = new GCMParameterSpec(128, content, 0, LENGTH_12);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key), params);
            byte[] decryptData = cipher.doFinal(content, LENGTH_12, content.length - LENGTH_12);
            return new String(decryptData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            LOGGER.error("getDecryptString error:", e);
        }
        return null;
    }

}
