package com.oner365.files.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.StorageEnum;
import com.oner365.files.config.properties.MinioProperties;
import com.oner365.files.dto.SysFileStorageDto;
import com.oner365.files.service.IFileStorageService;
import com.oner365.files.storage.IFileStorageClient;
import com.oner365.files.storage.condition.MinioStorageCondition;
import com.oner365.files.vo.SysFileStorageVo;
import com.oner365.util.DataUtils;

import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.http.Method;

/**
 * minio工具类
 *
 * @author zhaoyong
 */
@Component
@Conditional(MinioStorageCondition.class)
public class FileMinioClient implements IFileStorageClient {

  private final Logger logger = LoggerFactory.getLogger(FileMinioClient.class);

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
      logger.info("file path: {}", url);
      saveFileStorage(url, file.getOriginalFilename(), file.getSize());
      return url;
    } catch (Exception e) {
      logger.error("uploadFile MultipartFile Error:", e);
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
      logger.info("file path: {}", url);
      saveFileStorage(url, file.getName(), file.length());
      return url;
    } catch (Exception e) {
      logger.error("uploadFile File Error:", e);
    }
    return null;
  }

  @Override
  public byte[] download(String path) {
    logger.info("file download: {}", path);
    GetObjectArgs object = GetObjectArgs.builder().bucket(minioProperties.getBucket()).object(path).build();
    try (GetObjectResponse objectResponse = minioClient.getObject(object)) {
      return IOUtils.toByteArray(objectResponse);
    } catch (Exception e) {
      logger.error("download File Error:", e);
    }
    return new byte[0];
  }

  @Override
  public Boolean deleteFile(String path) {
    try {
      // 删除文件
      logger.info("file delete: {}", path);
      minioClient.removeObject(RemoveObjectArgs.builder().bucket(minioProperties.getBucket()).object(path).build());
      return fileStorageService.deleteById(path);
    } catch (Exception e) {
      logger.error("delete File Error:", e);
    }
    return Boolean.FALSE;
  }
  
  @Override
  public Long getFileSize(String path) {
    // 文件信息
    try {
      StatObjectResponse objectResponse = minioClient
          .statObject(StatObjectArgs.builder().bucket(minioProperties.getBucket()).object(path).build());
      logger.info("file path: {}", path);
      logger.info("file contentType: {}", objectResponse.contentType());
      logger.info("file size: {}", objectResponse.size());
      return objectResponse.size();
    } catch (Exception e) {
      logger.error("statObject error:", e);
    }
    return null;
  }

  @Override
  public String downloadPath(String path) {
    logger.info("file download: {}", path);
    try {
      String url = minioClient
          .getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(minioProperties.getBucket()).object(path)
              .method(Method.GET).expiry(PublicConstants.EXPIRE_TIME, TimeUnit.SECONDS).build());
      logger.info("file presigned url: {}", url);
      return url;
    } catch (Exception e) {
      logger.error("presignedObject error:", e);
    }
    return null;
  }

  @Override
  public StorageEnum getName() {
    return StorageEnum.MINIO;
  }

  private void saveFileStorage(String url, String fileName, long fileSize) {
    // save
    SysFileStorageVo entity = new SysFileStorageVo();
    entity.setFastdfsUrl(minioProperties.getUrl() + PublicConstants.DELIMITER + minioProperties.getBucket());
    entity.setId(url);
    entity.setCreateTime(LocalDateTime.now());
    entity.setDirectory(false);
    entity.setFileStorage(getName());
    entity.setFilePath(entity.getFastdfsUrl() + PublicConstants.DELIMITER + entity.getId());
    entity.setFileName(StringUtils.substringAfterLast(entity.getId(), PublicConstants.DELIMITER));
    entity.setDisplayName(fileName);
    entity.setFileSuffix(DataUtils.getExtension(fileName));
    entity.setSize(DataUtils.convertFileSize(fileSize));
    fileStorageService.save(entity);
  }
  
  @Override
  public SysFileStorageDto getFile(String id) {
    return fileStorageService.getById(id);
  }

}
