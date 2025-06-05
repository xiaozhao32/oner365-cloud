package com.oner365.deploy.utils;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能：数据工具类
 *
 * @author liutao
 */
public class DataUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataUtils.class);

    private DataUtils() {
    }

    /**
     * 新建目录
     * @param folderPath 目录
     */
    public static void createFolder(String folderPath) {
        try {
            File myFilePath = new File(folderPath);
            FileUtils.forceMkdir(myFilePath);
        }
        catch (Exception e) {
            LOGGER.error("Error createFolder:", e);
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
            else {
                LOGGER.error("copyDirectory not exists:{}", filePath);
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
            else {
                LOGGER.error("copyFile not exists:{}", filePath);
            }
        }
        catch (Exception e) {
            LOGGER.error("Error copyDirectory:", e);
        }
    }

}
