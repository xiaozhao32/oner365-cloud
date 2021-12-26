package com.oner365.util;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * base64 工具类
 * @author zhaoyong
 *
 */
public class Base64Utils {

    /**
     * Generate constructor
     */
    private Base64Utils() {
    }

    /**
     * base64 encode
     * @param binaryData 字节对象
     * @return byte[]
     */
    public static byte[] encodeBase64(byte[] binaryData) {
        return Base64.encodeBase64(binaryData);
    }

    /**
     * base64 encode
     * @param binaryData 字节对象
     * @return String
     */
    public static String encodeBase64String(byte[] binaryData) {
        return Base64.encodeBase64String(binaryData);
    }

    /**
     * base64 decode
     * @param base64Data 字节对象
     * @return byte[]
     */
    public static byte[] decodeBase64(byte[] base64Data) {
        return Base64.decodeBase64(base64Data);
    }

    /**
     * base64 decode
     * @param base64String 字符串对象
     * @return byte[]
     */
    public static String decodeBase64String(String base64String) {
        byte[] result = Base64.decodeBase64(base64String);
        return new String(result, StandardCharsets.UTF_8);
    }

    /**
     * 字符串每隔指定长度插入指定字符串
     * @param original         处理字符串
     * @param insertString     插入字符串
     * @param interval         间隔的字符长度
     * @return 字符串
     */
    public static String stringInsertByInterval(String original, String insertString, int interval) {
        if(original==null) {
            return "";
        }
        int len = original.length();
        if(interval>=len) {
            return original;
        }

        String rtnString;
        List<String> strList = new ArrayList<>();
        Pattern p = Pattern.compile("(.*" + interval + "}|.*)");
        Matcher m = p.matcher(original);
        while (m.find()) {
            strList.add(m.group());
        }
        strList =  strList.subList(0, strList.size()-1);
        rtnString = StringUtils.join(strList, insertString);
        return rtnString;
    }

}
