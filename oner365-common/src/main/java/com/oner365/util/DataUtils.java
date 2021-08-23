package com.oner365.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.map.LRUMap;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.CloneFailedException;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.oner365.common.constants.PublicConstants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 功能：数据工具类
 *
 * @author liutao
 *
 *
 */
public class DataUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataUtils.class);

    private static final String HEADER_UNKNOWN = "unknown";

    private static final String POINT = ".";

    private static final LRUMap<String, Integer> CACHE_MAP = new LRUMap<>(100);

    private static final int BUFFER_SIZE = 1024 * 8;

    private static final int IP_LENGTH = 15;
    private static final int IP_PART = 4;
    private static final String IP_LOCALHOST = "0:0:0:0:0:0:0:1";

    public static final String EMPTY_JSON = "{}";
    public static final char C_BACKSLASH = '\\';
    public static final char C_DELIMITER_START = '{';
    public static final char C_DELIMITER_END = '}';

    private DataUtils() {

    }

    /**
     * 幂等性判断
     *
     * @param id  主键
     * @param obj 对象
     * @return boolean
     */
    public static boolean judge(String id, Object obj) {
        synchronized (obj) {
            // 重复请求判断
            if (CACHE_MAP.containsKey(id)) {
                // 重复请求
                return false;
            }
            // 非重复请求，存储请求 ID
            CACHE_MAP.put(id, 1);
        }
        return true;
    }

    /**
     * 获取类型
     *
     * @param clazz 类
     * @return List<Map<String, Object>>
     */
    public static List<Map<String, Object>> getBeanAnnotationCol(Class<?> clazz) {
        try {
            Field field;
            Field[] fields = clazz.getDeclaredFields();
            for (Field value : fields) {
                value.setAccessible(true);
            }
            List<Map<String, Object>> list = Lists.newArrayList();
            for (Field value : fields) {
                field = clazz.getDeclaredField(value.getName());
                Column column = field.getAnnotation(Column.class);
                Method[] methods = Column.class.getDeclaredMethods();
                Map<String, Object> map = Maps.newHashMap();
                map.put(Field.class.getSimpleName().toLowerCase(), field.getName());
                if (column != null) {
                    for (Method method : methods) {
                        map.put(method.getName(), method.invoke(column));
                    }
                }
                JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
                Method[] joinMethods = JoinColumn.class.getDeclaredMethods();
                if (joinColumn != null) {
                    for (Method method : joinMethods) {
                        map.put(method.getName(), method.invoke(joinColumn));
                    }
                }
                list.add(map);
            }
            return list;
        } catch (Exception e) {
            LOGGER.error("Error getBeanAnnotationCol: ", e);
        }
        return Lists.newArrayList();
    }

    /**
     * 获得文件的扩展名
     *
     * @param fileName 文件名称
     * @return String
     */
    public static String getExtension(String fileName) {
        if (fileName != null) {
            int i = fileName.lastIndexOf('.');
            if (i > 0 && i < fileName.length() - 1) {
                return fileName.substring(i + 1).toLowerCase();
            }
        }
        return "";
    }

    /**
     * 新建目录
     *
     * @param folderPath 目录
     */
    public static void createFolder(String folderPath) {
        try {
            File myFilePath = new File(folderPath);
            FileUtils.forceMkdir(myFilePath);
        } catch (Exception e) {
            LOGGER.error("Error createFolder:", e);
        }
    }

    /**
     * 新建文件
     *
     * @param filePath    文件路径
     * @param fileContent 文件内容 只创建文件 传空
     */
    public static void createFile(String filePath, String fileContent) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                FileUtils.forceMkdir(new File(file.getParent()));
            }

            if (isEmpty(fileContent)) {
                boolean b = file.createNewFile();
                LOGGER.info("createFile: {}", b);
            } else {
                try (FileWriter fileWriter = new FileWriter(file);
                        PrintWriter printWriter = new PrintWriter(fileWriter)) {
                    printWriter.println(fileContent);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error createFile:", e);
        }
    }

    /**
     * 拷贝目录
     *
     * @param filePath   源文件目录
     * @param targetPath 目标文件路径
     */
    public static void copyDirectory(String filePath, String targetPath) {
        try {
            File srcFile = new File(filePath);
            File targetFile = new File(targetPath);
            if (srcFile.exists()) {
                FileUtils.copyDirectoryToDirectory(srcFile, targetFile);
            }
        } catch (Exception e) {
            LOGGER.error("Error copyDirectory:", e);
        }
    }

    /**
     * 拷贝文件
     *
     * @param filePath   源文件目录
     * @param targetPath 目标文件路径
     */
    public static void copyFile(String filePath, String targetPath) {
        try {
            File srcFile = new File(filePath);
            File targetFile = new File(targetPath);
            if (srcFile.exists()) {
                FileUtils.copyFileToDirectory(srcFile, targetFile);
            }
        } catch (Exception e) {
            LOGGER.error("Error copyDirectory:", e);
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    public static void deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                FileUtils.forceDelete(file);
            }
        } catch (IOException e) {
            LOGGER.error("Error deleteFile:", e);
        }
    }

    /**
     * 获得文件的名称
     *
     * @param f 文件名称
     * @return String
     */
    public static String getFileName(String f) {
        if (f != null) {
            String fileName = f.replace("\\", PublicConstants.DELIMITER);
            if (fileName.lastIndexOf(PublicConstants.DELIMITER) > 0) {
                fileName = fileName.substring(fileName.lastIndexOf(PublicConstants.DELIMITER) + 1);
            }
            int i = fileName.lastIndexOf('.');
            if (i > 0 && i < fileName.length() - 1) {
                return fileName.substring(0, i);
            }
        }
        return "";
    }

    /**
     * 对象转成字符串
     *
     * @param obj 字符串
     * @return String
     */
    public static String getString(Object obj) {
        String str = StringUtils.EMPTY;
        if (obj != null) {
            if (obj instanceof String) {
                str = (String) obj;
            } else {
                str = obj.toString();
            }
        }
        return str;
    }

    public static String format(String template, Object... params) {
        if (isEmpty(params) || isEmpty(template)) {
            return template;
        }
        return formatString(template, params);
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
     * 过滤空字符串
     *
     * @param str 字符串
     * @return String
     */
    public static String trimToNull(@Nullable String str) {
        return isEmpty(str) ? null : str.trim();
    }

    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }

    public static String formatString(final String strPattern, final Object... argArray) {
        if (isEmpty(strPattern) || isEmpty(argArray)) {
            return strPattern;
        }
        final int strPatternLength = strPattern.length();

        // 初始化定义好的长度以获得更好的性能
        StringBuilder builder = new StringBuilder(strPatternLength + 50);

        int handledPosition = 0;
        int delimiterIndex;// 占位符所在位置
        for (Object o : argArray) {
            delimiterIndex = strPattern.indexOf(EMPTY_JSON, handledPosition);
            if (delimiterIndex == -1) {
                if (handledPosition == 0) {
                    return strPattern;
                } else { // 字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
                    builder.append(strPattern, handledPosition, strPatternLength);
                    return builder.toString();
                }
            } else {
                if (delimiterIndex > 0 && strPattern.charAt(delimiterIndex - 1) == C_BACKSLASH) {
                    if (delimiterIndex > 1 && strPattern.charAt(delimiterIndex - 2) == C_BACKSLASH) {
                        // 转义符之前还有一个转义符，占位符依旧有效
                        builder.append(strPattern, handledPosition, delimiterIndex - 1);
                        builder.append(ConvertString.utf8Str(o));
                        handledPosition = delimiterIndex + 2;
                    } else {
                        // 占位符被转义
                        builder.append(strPattern, handledPosition, delimiterIndex - 1);
                        builder.append(C_DELIMITER_START);
                        handledPosition = delimiterIndex + 1;
                    }
                } else {
                    // 正常占位符
                    builder.append(strPattern, handledPosition, delimiterIndex);
                    builder.append(ConvertString.utf8Str(o));
                    handledPosition = delimiterIndex + 2;
                }
            }
        }
        // 加入最后一个占位符后所有的字符
        builder.append(strPattern, handledPosition, strPattern.length());

        return builder.toString();
    }

    /**
     * 去除下划线 返回首字母大写字符串
     *
     * @param name 名称
     * @return String
     */
    public static String builderName(String name) {
        if (DataUtils.isEmpty(name)) {
            return StringUtils.EMPTY;
        }
        StringBuilder temp = new StringBuilder();
        String[] str = name.split("_");
        if (str.length == 1) {
            temp.append(str[0].substring(0, 1).toUpperCase()).append(str[0].substring(1));
        } else {
            for (String s : str) {
                temp.append(s.substring(0, 1).toUpperCase());
                temp.append(s.substring(1).toLowerCase());
            }
        }
        return temp.toString();
    }

    /**
     * 去除下划线替换其他字符 返回首字母大写字符串
     *
     * @param name 名称
     * @return String
     */
    public static String builderName(String name, String splitName) {
        if (isEmpty(name)) {
            return StringUtils.EMPTY;
        }

        String split;
        if (splitName == null) {
            split = " ";
        } else {
            split = splitName;
        }
        StringBuilder temp = new StringBuilder();
        String[] str = name.split("_");
        String result;
        if (str.length == 1) {
            temp.append(str[0].substring(0, 1).toUpperCase()).append(str[0].substring(1));
            result = temp.toString();
        } else {
            for (String s : str) {
                temp.append(s.substring(0, 1).toUpperCase());
                temp.append(s.substring(1).toLowerCase());
                temp.append(split);
            }
            result = temp.substring(0, temp.length() - 1);
        }
        return result;
    }

    /**
     * 去除下划线 返回首字母小写字符串
     *
     * @param name 名称
     * @return String
     */
    public static String coderName(String name) {
        String result = builderName(name);
        result = result.substring(0, 1).toLowerCase() + result.substring(1);
        return result;
    }

    /**
     * 去除下划线 返回首字母小写字符串
     *
     * @param name 名称
     * @return String
     */
    public static String coderName(String name, String splitName) {
        String result = builderName(name, splitName);
        result = result.substring(0, 1).toLowerCase() + result.substring(1);
        return result;
    }

    /**
     * 克隆
     *
     * @param obj 对象
     * @return Object
     */
    public static Object clone(Object obj) {
        try (
                // 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                // 将流序列化成对象
                ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
                ObjectInputStream ois = new ObjectInputStream(bis)) {

            oos.writeObject(obj);
            return ois.readObject();
        } catch (Exception e) {
            throw new CloneFailedException(e);
        }
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
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

    /**
     * 获取本机名称
     *
     * @return String
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            LOGGER.error("Error getHostName:", e);
        }
        return "未知";
    }

    private static long getIp2long(String ip) {
        ip = ip.trim();
        String[] ips = ip.split("\\.");
        long ip2long = 0L;
        for (int i = 0; i < IP_PART; ++i) {
            ip2long = ip2long << 8 | Integer.parseInt(ips[i]);
        }
        return ip2long;
    }

    /**
     * 判断ip是否在段内
     *
     * @param ip        ip
     * @param ipSection ip段
     * @return boolean
     */
    public static boolean ipExistsInRange(String ip, String ipSection) {
        ipSection = ipSection.trim();
        ip = ip.trim();
        int idx = ipSection.indexOf('-');
        String beginIp = ipSection.substring(0, idx);
        String endIp = ipSection.substring(idx + 1);
        return getIp2long(beginIp) <= getIp2long(ip) && getIp2long(ip) <= getIp2long(endIp);
    }

    /**
     * 下载文件
     *
     * @param file     文件
     * @param fileName 文件名称
     * @return ResponseEntity
     */
    public static ResponseEntity<byte[]> download(File file, String fileName) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(file.length());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment",
                    URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()));
            return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
        } catch (IOException e) {
            LOGGER.error("Error download", e);
        }
        return null;
    }

    /**
     * 替换文件内容后生成文件 替换内容为空时直接生成(相当于拷贝文件)
     *
     * @param readFile  读取文件路径
     * @param writeFile 写入文件路径
     * @param items     替换模板内容：key替换成value, value不能为null, 可以为空字符串
     * @return boolean
     */
    public static boolean replaceContextFileCreate(String readFile, String writeFile, Map<String, Object> items) {
        try (FileInputStream fis = new FileInputStream(readFile)) {
            writeFile(fis, writeFile, items);
            LOGGER.debug("createFile success: {}", writeFile);
            return true;
        } catch (IOException e) {
            LOGGER.error("replaceContextFileCreate Error!", e);
        }
        return false;
    }

    private static void writeFile(InputStream is, String writeFile, Map<String, Object> items) {
        try (InputStreamReader isr = new InputStreamReader(is, Charset.defaultCharset());
                BufferedReader in = new BufferedReader(isr);
                FileOutputStream fos = new FileOutputStream(writeFile);
                OutputStreamWriter osw = new OutputStreamWriter(fos, Charset.defaultCharset());
                BufferedWriter out = new BufferedWriter(osw)) {

            // 替换对象
            String s;
            while ((s = in.readLine()) != null) {
                String context = replaceContext(items, s);
                out.write(context);
                out.newLine();
            }
            out.flush();
        } catch (IOException e) {
            LOGGER.error("writeFile Error!", e);
        }

    }

    /**
     * 替换内容
     *
     * @param items 内容
     * @param s     结果
     */
    private static String replaceContext(Map<String, Object> items, String s) {
        String result = s;
        if (items != null) {
            for (Map.Entry<String, Object> entry : items.entrySet()) {
                final String key = entry.getKey();
                String value = StringUtils.EMPTY;
                if (items.get(key) != null) {
                    value = items.get(key).toString();
                }
                result = result.replace(key, value);
            }
        }
        return result;
    }

    /**
     * File 转换成 MultipartFile
     *
     * @param file 文件
     * @return MultipartFile
     */
    public static MultipartFile convertMultipartFile(File file) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem(file.getName(), MediaType.TEXT_PLAIN_VALUE, true, file.getName());
        int bytesRead;
        byte[] buffer = new byte[BUFFER_SIZE];
        try (FileInputStream fis = new FileInputStream(file); OutputStream os = item.getOutputStream()) {
            while ((bytesRead = fis.read(buffer, 0, BUFFER_SIZE)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            LOGGER.error("Error convertMultipartFile:", e);
        }
        return new CommonsMultipartFile(item);
    }
}
