package com.oner365.data.commons.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.map.LRUMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.CloneFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import com.oner365.data.commons.constants.PublicConstants;

/**
 * 功能：数据工具类
 *
 * @author liutao
 */
public class DataUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataUtils.class);

    private static final LRUMap<String, Integer> CACHE_MAP = new LRUMap<>(100);

    public static final String PARENT_FILE = "..";

    public static final String EMPTY_JSON = "{}";

    public static final char C_BACKSLASH = '\\';

    public static final char C_DELIMITER_START = '{';

    public static final char C_DELIMITER_END = '}';

    public static final String C_QUARTER = ",";

    public static final String KB = "KB";

    public static final String MB = "MB";

    public static final String GB = "GB";

    private DataUtils() {

    }

    /**
     * 幂等性判断
     * @param id 主键
     * @param obj 对象
     * @return boolean
     */
    public static boolean judge(String id, Object obj) {
        Object object = obj;
        synchronized (object) {
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
     * 获得文件的扩展名
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
        return PublicConstants.EMPTY;
    }

    /**
     * 新建目录
     * @param folderPath 目录
     */
    public static void createFolder(String folderPath) {
        try {
            File myFilePath = new File(folderPath);
            if (!myFilePath.exists()) {
                FileUtils.forceMkdir(myFilePath);
            }
        }
        catch (Exception e) {
            LOGGER.error("Error createFolder:", e);
        }
    }

    /**
     * 新建文件
     * @param filePath 文件路径
     * @param fileContent 文件内容 只创建文件 传空
     */
    public static void createFile(String filePath, String fileContent) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                FileUtils.forceMkdir(new File(file.getParent()));
            }

            if (DataUtils.isEmpty(fileContent)) {
                boolean b = file.createNewFile();
                LOGGER.info("createFile: {}", b);
            }
            else {
                try (FileWriter fileWriter = new FileWriter(file);
                        PrintWriter printWriter = new PrintWriter(fileWriter)) {
                    printWriter.println(fileContent);
                }
            }
        }
        catch (Exception e) {
            LOGGER.error("Error createFile:", e);
        }
    }

    /**
     * 拷贝目录
     * @param filePath 源文件目录
     * @param targetPath 目标文件路径
     */
    public static void copyDirectory(String filePath, String targetPath) {
        try {
            File srcFile = new File(filePath);
            File targetFile = new File(targetPath);
            if (srcFile.exists()) {
                FileUtils.copyDirectoryToDirectory(srcFile, targetFile);
            }
        }
        catch (Exception e) {
            LOGGER.error("Error copyDirectory:", e);
        }
    }

    /**
     * 拷贝文件
     * @param filePath 源文件目录
     * @param targetPath 目标文件路径
     */
    public static void copyFile(String filePath, String targetPath) {
        try {
            File srcFile = new File(filePath);
            File targetFile = new File(targetPath);
            if (srcFile.exists()) {
                FileUtils.copyFileToDirectory(srcFile, targetFile);
            }
        }
        catch (Exception e) {
            LOGGER.error("Error copyDirectory:", e);
        }
    }

    /**
     * 删除文件
     * @param filePath 文件路径
     */
    public static void deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                FileUtils.forceDelete(file);
            }
        }
        catch (IOException e) {
            LOGGER.error("Error deleteFile:", e);
        }
    }

    /**
     * 获取文件对象
     * @param filePath 文件地址
     * @param fileName 文件名称
     * @return File 文件
     */
    public static File getFile(String filePath, String fileName) {
        return getFile(filePath + File.separator + fileName);
    }

    /**
     * 获取文件对象
     * @param filePath 文件地址
     * @return File 文件
     */
    public static File getFile(String filePath) {
        // 禁止目录上跳级别
        if (StringUtils.contains(filePath, PARENT_FILE)) {
            return null;
        }
        File file = new File(filePath);
        if (file.exists()) {
            return file;
        }
        return null;
    }

    /**
     * 获取文件对象
     * @param filePath 文件地址
     * @param fileName 文件名称
     * @return FileOutputStream 文件
     * @throws FileNotFoundException 异常
     */
    public static FileOutputStream getFileOutputStream(String filePath, String fileName) throws FileNotFoundException {
        createFolder(filePath);
        return getFileOutputStream(filePath + File.separator + fileName);
    }

    /**
     * 获取文件对象
     * @param filePath 文件地址
     * @return FileOutputStream 文件
     * @throws FileNotFoundException 异常
     */
    public static FileOutputStream getFileOutputStream(String filePath) throws FileNotFoundException {
        // 禁止目录上跳级别
        if (StringUtils.contains(filePath, PARENT_FILE)) {
            return null;
        }
        return new FileOutputStream(filePath);
    }

    /**
     * 获得文件的名称
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
        return PublicConstants.EMPTY;
    }

    /**
     * 对象转成字符串
     * @param obj 字符串
     * @return String
     */
    public static String getString(Object obj) {
        String str = StringUtils.EMPTY;
        if (obj != null) {
            if (obj instanceof String) {
                str = (String) obj;
            }
            else {
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
     * 过滤空字符串 返回 Null
     * @param str 字符串
     * @return String
     */
    public static String trimToNull(String str) {
        return isEmpty(str) ? null : str.trim();
    }

    /**
     * 过滤空字符串 返回空字符串
     * @param str 字符串
     * @return String
     */
    public static String trimToEmpty(String str) {
        return isEmpty(str) ? PublicConstants.EMPTY : str.trim();
    }

    /**
     * 字节转换
     * @param size 字节大小
     * @return 转换后值
     */
    public static String convertFileSize(long size) {
        long kb = PublicConstants.BYTE_SIZE;
        long mb = kb * PublicConstants.BYTE_SIZE;
        long gb = mb * PublicConstants.BYTE_SIZE;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        }
        else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        }
        else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        }
        else {
            return String.format("%d B", size);
        }
    }

    /**
     * 转换字节
     * @param size 大小
     * @return long
     */
    public static long convertSize(String size) {
        long kb = PublicConstants.BYTE_SIZE;
        long mb = kb * PublicConstants.BYTE_SIZE;
        long gb = mb * PublicConstants.BYTE_SIZE;
        long b = Long.parseLong(size.substring(0, size.length() - 2));
        if (size.contains(KB)) {
            return b * kb;
        }
        if (size.contains(MB)) {
            return b * mb;
        }
        if (size.contains(GB)) {
            return b * gb;
        }
        return b;
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
                }
                else { // 字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
                    builder.append(strPattern, handledPosition, strPatternLength);
                    return builder.toString();
                }
            }
            else {
                if (delimiterIndex > 0 && strPattern.charAt(delimiterIndex - 1) == C_BACKSLASH) {
                    if (delimiterIndex > 1 && strPattern.charAt(delimiterIndex - 2) == C_BACKSLASH) {
                        // 转义符之前还有一个转义符，占位符依旧有效
                        builder.append(strPattern, handledPosition, delimiterIndex - 1);
                        builder.append(ConvertString.utf8Str(o));
                        handledPosition = delimiterIndex + 2;
                    }
                    else {
                        // 占位符被转义
                        builder.append(strPattern, handledPosition, delimiterIndex - 1);
                        builder.append(C_DELIMITER_START);
                        handledPosition = delimiterIndex + 1;
                    }
                }
                else {
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
        }
        else {
            for (String s : str) {
                temp.append(s.substring(0, 1).toUpperCase());
                temp.append(s.substring(1).toLowerCase());
            }
        }
        return temp.toString();
    }

    /**
     * 去除下划线替换其他字符 返回首字母大写字符串
     * @param name 名称
     * @return String
     */
    public static String builderName(String name, String splitName) {
        if (DataUtils.isEmpty(name)) {
            return StringUtils.EMPTY;
        }

        String split;
        if (splitName == null) {
            split = " ";
        }
        else {
            split = splitName;
        }
        StringBuilder temp = new StringBuilder();
        String[] str = name.split("_");
        String result;
        if (str.length == 1) {
            temp.append(str[0].substring(0, 1).toUpperCase()).append(str[0].substring(1));
            result = temp.toString();
        }
        else {
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
     * @param name 名称
     * @return String
     */
    public static String coderName(String name, String splitName) {
        String result = builderName(name, splitName);
        result = result.substring(0, 1).toLowerCase() + result.substring(1);
        return result;
    }

    /**
     * 查找指定字符串是否匹配指定字符串列表中的任意一个字符串
     * @param str 指定字符串
     * @param strs 需要检查的字符串数组
     * @return 是否匹配
     */
    public static boolean matches(String str, List<String> strs) {
        if (isEmpty(str) || isEmpty(strs)) {
            return false;
        }
        for (String pattern : strs) {
            if (isMatch(pattern, str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断url是否与规则配置: ? 表示单个字符; * 表示一层路径内的任意字符串，不可跨层级; ** 表示任意层路径;
     * @param pattern 匹配规则
     * @param url 需要匹配的url
     * @return 是否匹配
     */
    public static boolean isMatch(String pattern, String url) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(pattern, url);
    }

    /**
     * 克隆
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
        }
        catch (Exception e) {
            throw new CloneFailedException(e);
        }
    }

    /**
     * 替换文件内容后生成文件 替换内容为空时直接生成(相当于拷贝文件)
     * @param readFile 读取文件路径
     * @param writeFile 写入文件路径
     * @param items 替换模板内容：key替换成value, value不能为null, 可以为空字符串
     * @return boolean
     */
    public static boolean replaceContextFileCreate(String readFile, String writeFile, Map<String, Object> items) {
        try (FileInputStream fis = new FileInputStream(readFile)) {
            writeFile(fis, writeFile, items);
            LOGGER.debug("createFile success: {}", writeFile);
            return true;
        }
        catch (IOException e) {
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
        }
        catch (IOException e) {
            LOGGER.error("writeFile Error!", e);
        }

    }

    /**
     * 替换内容
     * @param items 内容
     * @param s 结果
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

}
