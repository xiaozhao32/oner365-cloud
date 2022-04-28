package com.oner365.gateway.util;

import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * 工具类
 * 
 * @author zhaoyong
 *
 */
public class DataUtils {
  
    private static final Logger LOGGER = LoggerFactory.getLogger(DataUtils.class);
  
    private static final String HEADER_UNKNOWN = "unknown";

    private static final String POINT = ".";

    private static final int IP_LENGTH = 15;
    private static final String IP_LOCALHOST = "0:0:0:0:0:0:0:1";
    
    private DataUtils() {
    }

    /**
     * 判断一个Object 是否为空
     *
     * @param obj 对象
     * @return boolean
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof Optional) {
            return !((Optional<?>) obj).isPresent();
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        }

        // else
        return false;
    }
    
    /**
     * 获取ip
     *
     * @param request ServerHttpRequest
     * @return String
     */
    public static String getIpAddress(ServerHttpRequest request) {
        String ip = request.getHeaders().getFirst("X-Real-IP");
        if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
            InetSocketAddress address = request.getRemoteAddress();
            if (address != null && address.getAddress() != null) {
                ip = address.getAddress().getHostAddress();
            }
        }
        if (IP_LOCALHOST.equals(ip)) {
            ip = getLocalhost();
        }
        if (ip != null && ip.length() > IP_LENGTH && ip.contains(POINT)) {
            ip = StringUtils.substringAfterLast(ip, ",");
        }
        return ip;
    }

    /**
     * 获取本机ip
     *
     * @return String
     */
    public static String getLocalhost() {
        try {
            InetAddress inet = InetAddress.getLocalHost();
            return inet.getHostAddress();
        } catch (UnknownHostException e) {
            LOGGER.error("Error getLocalhost:", e);
        }
        return null;
    }

}
