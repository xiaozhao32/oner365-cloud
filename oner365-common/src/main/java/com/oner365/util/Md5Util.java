package com.oner365.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MD5加密工具类
 * @author zhaoyong
 */
public class Md5Util {

    private static final Logger LOGGER = LoggerFactory.getLogger(Md5Util.class);

    private static final ThreadLocal<Md5Util> LOCAL = new ThreadLocal<>();

    /**
     * Generate constructor
     */
    private Md5Util() {
    }
    
    public void remove() {
        LOCAL.remove();
    }

    /**
     * 获取单例
     * @return MD5Util
     */
    public static Md5Util getInstance() {
        Md5Util instance = LOCAL.get();
        if (instance == null) {
            instance = new Md5Util();
            LOCAL.set(instance);
        }
        return instance;
    }

    /**
     * md5
     * @param data 字符串
     * @return String
     */
    public String getMd5(String data) {
        String result = null;
        if (data != null) {
            result = DigestUtils.md5Hex(data);
        }
        return result;
    }

    /**
     * md5
     * @param data 字符串
     * @return String
     */
    public String getMd5(byte[] data) {
        String result = null;
        if (data != null) {
            result = DigestUtils.md5Hex(data);
        }
        return result;
    }

    /**
     * md5
     * @param data 字符串
     * @return byte[]
     */
    public byte[] md5(String data) {
        byte[] result = null;
        if (data != null) {
            result = DigestUtils.md5(data);
        }
        return result;
    }

    /**
     * sha1
     * @param data 字符串
     * @return byte[]
     */
    public byte[] sha1(String data) {
        byte[] result = null;
        if (data != null) {
            result = DigestUtils.sha1(data);
        }
        return result;
    }

    /**
     * sha1
     * @param data 字符串
     * @return String
     */
    public String getSha1(String data) {
        String result = null;
        if (data != null) {
            result = DigestUtils.sha1Hex(data);
        }
        return result;
    }

    /**
     * signature
     * @param key 标识
     * @param data 字符串
     * @param type 类型
     * @return byte[]
     */
    public byte[] getSignature(byte[] key, byte[] data, String type) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, type);
        try {
            Mac mac = Mac.getInstance(type);
            mac.init(secretKeySpec);
            return mac.doFinal(data);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            LOGGER.error("Error getSignature:", e);
        }
        return ArrayUtils.EMPTY_BYTE_ARRAY;
    }

}
