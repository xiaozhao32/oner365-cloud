package com.oner365.files.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.common.config.properties.DefaultFileProperties;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.StorageEnum;
import com.oner365.files.config.properties.MinioProperties;
import com.oner365.files.service.IFileStorageService;
import com.oner365.files.storage.IFileStorageClient;
import com.oner365.files.storage.condition.MinioStorageCondition;
import com.oner365.files.vo.SysFileStorageVo;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;

import io.minio.DownloadObjectArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;

/**
 * minio工具类
 * 
 * @author zhaoyong
 */
@Component
@Conditional(MinioStorageCondition.class)
public class FileMinioClient implements IFileStorageClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileMinioClient.class);

  @Autowired
  private DefaultFileProperties fileProperties;

  @Autowired
  private MinioProperties minioProperties;

  @Autowired
  private MinioClient minioClient;

  @Autowired
  private IFileStorageService fileStorageService;

  @Override
  public String uploadFile(MultipartFile file, String directory) {
    try (InputStream inputStream = file.getInputStream()) {
      String path = file.getOriginalFilename();
      if (!DataUtils.isEmpty(directory)) {
        path = directory + PublicConstants.DELIMITER + file.getOriginalFilename();
      }
      ObjectWriteResponse writeResponse = minioClient
          .putObject(PutObjectArgs.builder().bucket(minioProperties.getBucket()).object(path)
              .stream(inputStream, file.getSize(), -1).contentType(file.getContentType()).build());
      String url = writeResponse.object();
      LOGGER.info("file path: {}", url);
      saveFileStorage(url, file.getOriginalFilename(), file.getSize());
      return url;
    } catch (Exception e) {
      LOGGER.error("uploadFile MultipartFile Error:", e);
    }
    return null;
  }

  @Override
  public String uploadFile(File file, String directory) {
    try (InputStream inputStream = new FileInputStream(file)) {
      String path = file.getName();
      if (!DataUtils.isEmpty(directory)) {
        path = directory + PublicConstants.DELIMITER + file.getName();
      }
      ObjectWriteResponse writeResponse = minioClient.putObject(PutObjectArgs.builder()
          .bucket(minioProperties.getBucket()).object(path).stream(inputStream, file.length(), -1).build());
      String url = writeResponse.object();
      LOGGER.info("file path: {}", url);
      saveFileStorage(url, file.getName(), file.length());
      return url;
    } catch (Exception e) {
      LOGGER.error("uploadFile File Error:", e);
    }
    return null;
  }

  @Override
  public byte[] download(String path) {
    try {
      String fullpath = fileProperties.getDownload() + PublicConstants.DELIMITER + path;
      // 判断文件夹是否存在则创建
      File file = DataUtils.getFile(fullpath);
      FileUtils.forceMkdirParent(file);
      // 下载
      minioClient.downloadObject(
          DownloadObjectArgs.builder().bucket(minioProperties.getBucket()).object(path).filename(fullpath).build());

      if (file.exists()) {
        return FileUtil.readAsByteArray(file);
      }
    } catch (Exception e) {
      LOGGER.error("download File Error:", e);
    }

    return null;
  }

  @Override
  public void deleteFile(String fileUrl) {
    try {
      // 删除文件
      minioClient.removeObject(RemoveObjectArgs.builder().bucket(minioProperties.getBucket()).object(fileUrl).build());
      fileStorageService.deleteById(fileUrl);
    } catch (Exception e) {
      LOGGER.error("delete File Error:", e);
    }
  }

  @Override
  public StorageEnum getName() {
    return StorageEnum.MINIO;
  }

  private void saveFileStorage(String url, String fileName, long fileSize) {
    // save
    SysFileStorageVo entity = new SysFileStorageVo();
    entity.setFastdfsUrl(minioProperties.getUrl());
    entity.setId(StringUtils.replace(url, entity.getFastdfsUrl() + PublicConstants.DELIMITER, ""));
    entity.setCreateTime(DateUtil.getDate());
    entity.setDirectory(false);
    entity.setFileStorage(getName().getCode());
    entity.setFilePath(url);
    entity.setFileName(StringUtils.substringAfterLast(url, PublicConstants.DELIMITER));
    entity.setDisplayName(fileName);
    entity.setFileSuffix(DataUtils.getExtension(fileName));
    entity.setSize(DataUtils.convertFileSize(fileSize));
    fileStorageService.save(entity);
  }

}
