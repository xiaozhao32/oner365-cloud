
package com.oner365.files.util;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.StorageEnum;
import com.oner365.files.vo.SysFileStorageVo;
import com.oner365.util.DataUtils;

/**
 * 本地文件上传
 * 
 * @author zhaoyong
 *
 */
public class FileLocalUploadUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileLocalUploadUtils.class);

  private static final String FILE_HTTP = "http://";

  private FileLocalUploadUtils() {
  }

  /**
   * 上传文件
   * 
   * @param file      文件
   * @param fileWeb   web访问地址
   * @param filePath  文件上传根目录
   * @param uploadDir 上传的文件夹
   * @param maxLength 文件长度
   * @return SysFileStorage
   */
  public static SysFileStorageVo upload(MultipartFile file, StorageEnum storageEnum, String randomName, String fileWeb,
      String filePath, String uploadDir, long maxLength) {
    try {
      long fileNameLength = file.getSize();
      if (fileNameLength > maxLength) {
        LOGGER.error("文件长度过大，清重新上传文件");
        return null;
      }

      String fileName = extractFilename(file, randomName);
      File desc = getAbsoluteFile(filePath, uploadDir, fileName);
      file.transferTo(desc);
      // http url
      return getPathFileName(file, storageEnum, fileWeb, uploadDir, fileName);
    } catch (Exception e) {
      LOGGER.error("upload error:", e);
    }
    return null;
  }

  private static String extractFilename(MultipartFile file, String randomName) {
    String extension = DataUtils.getExtension(file.getOriginalFilename());
    return randomName + "." + extension;
  }

  private static File getAbsoluteFile(String filePath, String uploadDir, String fileName) throws IOException {
    String uploadPath = StringUtils.EMPTY;
    if (!DataUtils.isEmpty(uploadDir)) {
      uploadPath = uploadDir + PublicConstants.DELIMITER;
    }

    String absoluteFile = filePath + File.separator + uploadPath + fileName;
    LOGGER.info("Local upload File path: {}", absoluteFile);
    File file = new File(absoluteFile);
    if (!file.exists()) {
      FileUtils.forceMkdir(new File(file.getParent()));
    }
    return file;
  }

  private static SysFileStorageVo getPathFileName(MultipartFile file, StorageEnum storageEnum, String fileWeb,
      String uploadDir, String fileName) {
    String uploadPath = StringUtils.EMPTY;
    if (!DataUtils.isEmpty(uploadDir)) {
      uploadPath = uploadDir + PublicConstants.DELIMITER;
    }
    String fileUrl = fileWeb + PublicConstants.DELIMITER + uploadPath + fileName;

    String ss = fileUrl.replace(FILE_HTTP, "");
    String fastUrl = StringUtils.substringBefore(ss, PublicConstants.DELIMITER);
    String id = uploadPath + fileName;
    // save file
    SysFileStorageVo fileEntity = new SysFileStorageVo();
    fileEntity.setId(id);
    fileEntity.setFileName(fileName);
    fileEntity.setFastdfsUrl(FILE_HTTP + fastUrl);
    fileEntity.setFileStorage(storageEnum);
    fileEntity.setFilePath(fileUrl);
    fileEntity.setDisplayName(file.getOriginalFilename());
    fileEntity.setFileSuffix(DataUtils.getExtension(file.getOriginalFilename()));
    fileEntity.setSize(DataUtils.convertFileSize(file.getSize()));
    fileEntity.setDirectory(false);
    fileEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
    return fileEntity;
  }

  /**
   * 下载文件
   * 
   * @param filePath 文件根路径
   * @param fileUrl  文件地址
   * @return byte[]
   */
  public static byte[] download(String filePath, String fileUrl) {
    String path = filePath + PublicConstants.DELIMITER + fileUrl;
    LOGGER.info("Local download File path: {}", path);
    File file = DataUtils.getFile(path);
    try {
      return FileUtil.readAsByteArray(file);
    } catch (Exception e) {
      LOGGER.error("download error:", e);
    }
    return new byte[0];
  }

}
