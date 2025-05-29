package com.oner365.data.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oner365.data.commons.constants.PublicConstants;

/**
 * 文件加密工具类
 *
 * @author liutao
 *
 */
public class FileSms4Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileSms4Utils.class);

    private static final String PLACEHOLDER = "0123456789101112";

    private static final String DEFAULT_KEY = "01234567891011121314151617181920";

    private static final int KEY_SIZE = 32;

    private static final int BEGIN = 0;

    private static final int PLACEHOLDER_SIZE = 16;

    private static final int PART_SIZE = PublicConstants.BYTE_SIZE * PublicConstants.BYTE_SIZE;

    private FileSms4Utils() {
    }

    /**
     * 使用文件前32位字节md5做加密key进行sms4加密文件
     * @param file 加密源文件
     * @param encodeFilePath 生成加密文件地址
     */
    public static void encode(File file, String encodeFilePath) {
        byte[] key = new byte[KEY_SIZE];
        try (FileInputStream fis = new FileInputStream(file)) {
            int len = fis.read(key, BEGIN, KEY_SIZE);
            if (len < KEY_SIZE) {
                key = DEFAULT_KEY.getBytes();
            }
            byte[] content = FileUtils.readFileToByteArray(file);
            byte[] contentPlaceholder = new byte[content.length + PLACEHOLDER_SIZE];
            System.arraycopy(content, BEGIN, contentPlaceholder, BEGIN, content.length);
            System.arraycopy(PLACEHOLDER.getBytes(), BEGIN, contentPlaceholder,
                    contentPlaceholder.length - PLACEHOLDER_SIZE, PLACEHOLDER_SIZE);
            byte[] sms4Content = Cipher.encodeSms4(contentPlaceholder, Md5Util.getInstance().getMd5(key).getBytes());
            byte[] encodeByte = new byte[key.length + sms4Content.length];
            System.arraycopy(key, BEGIN, encodeByte, BEGIN, key.length);
            System.arraycopy(sms4Content, BEGIN, encodeByte, key.length, sms4Content.length);
            FileUtils.writeByteArrayToFile(new File(encodeFilePath), encodeByte);
        }
        catch (IOException e) {
            LOGGER.error("encode error", e);
        }
    }

    /**
     * 使用文件前32位字节md5做解密key进行sms4解密文件
     * @param file 解密源文件
     * @param decodeFilePath 生成解密文件地址
     */
    public static void decode(File file, String decodeFilePath) {
        byte[] key = new byte[KEY_SIZE];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(key, BEGIN, KEY_SIZE);
            byte[] content = FileUtils.readFileToByteArray(file);
            byte[] encodeContent = new byte[content.length - KEY_SIZE];
            System.arraycopy(content, KEY_SIZE, encodeContent, BEGIN, encodeContent.length);
            byte[] deContent = Cipher.decodeSms4(encodeContent, Md5Util.getInstance().getMd5(key).getBytes());
            FileUtils.writeByteArrayToFile(new File(decodeFilePath),
                    Arrays.copyOf(deContent, deContent.length - PLACEHOLDER_SIZE));
        }
        catch (IOException e) {
            LOGGER.error("decode error", e);
        }
    }

    /**
     * 使用文件前32位字节md5做加密key进行sms4加密文件,只加密文件最后1MB
     * @param file 加密源文件
     * @param encodeFilePath 生成加密文件地址
     */
    public static void encodePart(File file, String encodeFilePath) {
        byte[] key = new byte[KEY_SIZE];
        try (FileInputStream fis = new FileInputStream(file)) {
            int len = fis.read(key, BEGIN, KEY_SIZE);
            if (len < KEY_SIZE) {
                key = DEFAULT_KEY.getBytes();
            }
            byte[] content = FileUtils.readFileToByteArray(file);
            if (content.length > PART_SIZE) {
                byte[] encodeContent = new byte[PART_SIZE + PLACEHOLDER_SIZE];
                System.arraycopy(content, content.length - PART_SIZE, encodeContent, BEGIN, PART_SIZE);
                System.arraycopy(PLACEHOLDER.getBytes(), BEGIN, encodeContent, PART_SIZE, PLACEHOLDER_SIZE);
                byte[] sms4Content = Cipher.encodeSms4(encodeContent, Md5Util.getInstance().getMd5(key).getBytes());
                byte[] encodeByte = new byte[key.length + content.length + PLACEHOLDER_SIZE];
                System.arraycopy(key, BEGIN, encodeByte, BEGIN, key.length);
                System.arraycopy(content, BEGIN, encodeByte, KEY_SIZE, content.length - PART_SIZE);
                System.arraycopy(sms4Content, BEGIN, encodeByte, encodeByte.length - PART_SIZE - PLACEHOLDER_SIZE,
                        sms4Content.length);
                FileUtils.writeByteArrayToFile(new File(encodeFilePath), encodeByte);
            }
            else {
                encode(file, encodeFilePath);
            }
        }
        catch (IOException e) {
            LOGGER.error("encodePart error", e);
        }
    }

    /**
     * 使用文件前32位字节md5做解密key进行sms4解密文件,只解密文件最后1MB
     * @param file 解密源文件
     * @param decodeFilePath 生成解密文件地址
     */
    public static void decodePart(File file, String decodeFilePath) {
        byte[] key = new byte[KEY_SIZE];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(key, BEGIN, KEY_SIZE);
            byte[] content = FileUtils.readFileToByteArray(file);
            if (content.length > PART_SIZE) {
                byte[] allContent = new byte[content.length - KEY_SIZE];
                System.arraycopy(content, KEY_SIZE, allContent, BEGIN, allContent.length);
                byte[] encodeContent = Arrays.copyOfRange(allContent, allContent.length - PART_SIZE - PLACEHOLDER_SIZE,
                        allContent.length);
                byte[] rightContent = Cipher.decodeSms4(encodeContent, Md5Util.getInstance().getMd5(key).getBytes());
                byte[] leftContent = Arrays.copyOfRange(allContent, BEGIN,
                        allContent.length - PART_SIZE - PLACEHOLDER_SIZE);
                byte[] deContent = new byte[allContent.length - PLACEHOLDER_SIZE];
                System.arraycopy(leftContent, BEGIN, deContent, BEGIN, leftContent.length);
                System.arraycopy(rightContent, BEGIN, deContent, leftContent.length,
                        rightContent.length - PLACEHOLDER_SIZE);
                FileUtils.writeByteArrayToFile(new File(decodeFilePath), deContent);
            }
            else {
                decode(file, decodeFilePath);
            }
        }
        catch (IOException e) {
            LOGGER.error("decodePart error", e);
        }
    }

    /**
     * 使用文件前32位字节md5做加密key进行sms4加密文件,只加密文件最后1MB
     * @param file 加密源文件
     * @param encodeFilePath 生成加密文件地址
     */
    public static void encodePartNoPlaceholder(File file, String encodeFilePath) {
        byte[] key = new byte[KEY_SIZE];
        try (FileInputStream fis = new FileInputStream(file)) {
            int len = fis.read(key, BEGIN, KEY_SIZE);
            if (len < KEY_SIZE) {
                key = DEFAULT_KEY.getBytes();
            }
            byte[] content = FileUtils.readFileToByteArray(file);
            if (content.length > PART_SIZE) {
                byte[] encodeContent = new byte[PART_SIZE];
                System.arraycopy(content, content.length - PART_SIZE, encodeContent, BEGIN, PART_SIZE);
                byte[] sms4Content = Cipher.encodeSms4(encodeContent, Md5Util.getInstance().getMd5(key).getBytes());
                byte[] encodeByte = new byte[key.length + content.length];
                System.arraycopy(key, BEGIN, encodeByte, BEGIN, key.length);
                System.arraycopy(content, BEGIN, encodeByte, KEY_SIZE, content.length - PART_SIZE);
                System.arraycopy(sms4Content, BEGIN, encodeByte, encodeByte.length - PART_SIZE, sms4Content.length);
                FileUtils.writeByteArrayToFile(new File(encodeFilePath), encodeByte);
            }
            else {
                encode(file, encodeFilePath);
            }
        }
        catch (IOException e) {
            LOGGER.error("encodePartNoPlaceholder error", e);
        }
    }

    /**
     * 使用文件前32位字节md5做解密key进行sms4解密文件,只解密文件最后1MB
     * @param file 解密源文件
     * @param decodeFilePath 生成解密文件地址
     */
    public static void decodePartNoPlaceholder(File file, String decodeFilePath) {
        byte[] key = new byte[KEY_SIZE];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(key, BEGIN, KEY_SIZE);
            byte[] content = FileUtils.readFileToByteArray(file);
            if (content.length > PART_SIZE) {
                byte[] allContent = new byte[content.length - KEY_SIZE];
                System.arraycopy(content, KEY_SIZE, allContent, BEGIN, allContent.length);
                byte[] encodeContent = Arrays.copyOfRange(allContent, allContent.length - PART_SIZE, allContent.length);
                byte[] rightContent = Cipher.decodeSms4(encodeContent, Md5Util.getInstance().getMd5(key).getBytes());
                byte[] leftContent = Arrays.copyOfRange(allContent, BEGIN, allContent.length - PART_SIZE);
                byte[] deContent = new byte[allContent.length];
                System.arraycopy(leftContent, BEGIN, deContent, BEGIN, leftContent.length);
                System.arraycopy(rightContent, BEGIN, deContent, leftContent.length, rightContent.length);
                FileUtils.writeByteArrayToFile(new File(decodeFilePath), deContent);
            }
            else {
                decode(file, decodeFilePath);
            }
        }
        catch (IOException e) {
            LOGGER.error("decodePartNoPlaceholder error", e);
        }
    }

}
