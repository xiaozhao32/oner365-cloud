package com.oner365.files.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
import com.oner365.files.service.IFileStorageService;
import com.oner365.files.storage.IFileStorageClient;
import com.oner365.files.storage.condition.MinioStorageCondition;
import com.oner365.files.vo.SysFileStorageVo;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;

import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
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
    try (GetObjectResponse objectResponse = minioClient
        .getObject(GetObjectArgs.builder().bucket(minioProperties.getBucket()).object(path).build())) {
      return readAsByteArray(objectResponse);
    } catch (Exception e) {
      LOGGER.error("download File Error:", e);
    }
    return new byte[0];
  }
  
  /**
   * apache common-io 
   * 
   * @param objectResponse GetObjectResponse
   * @return byte[]
   * @throws IOException
   */
  public static byte[] readAsByteArray(GetObjectResponse objectResponse) throws IOException {
    int size = 1024;
    byte[] ba = new byte[size];
    int readSoFar = 0;

    while (true) {
      int nRead = objectResponse.read(ba, readSoFar, size - readSoFar);
      if (nRead == -1) {
        break;
      }
      readSoFar += nRead;
      if (readSoFar == size) {
        int newSize = size * 2;
        byte[] newBa = new byte[newSize];
        System.arraycopy(ba, 0, newBa, 0, size);
        ba = newBa;
        size = newSize;
      }
    }

    byte[] newBa = new byte[readSoFar];
    System.arraycopy(ba, 0, newBa, 0, readSoFar);
    return newBa;
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
    entity.setFastdfsUrl(minioProperties.getUrl() + PublicConstants.DELIMITER + minioProperties.getBucket());
    entity.setId(StringUtils.replace(url, entity.getFastdfsUrl() + PublicConstants.DELIMITER, ""));
    entity.setCreateTime(DateUtil.getDate());
    entity.setDirectory(false);
    entity.setFileStorage(getName());
    entity.setFilePath(entity.getFastdfsUrl() + PublicConstants.DELIMITER + url);
    entity.setFileName(StringUtils.substringAfterLast(url, PublicConstants.DELIMITER));
    entity.setDisplayName(fileName);
    entity.setFileSuffix(DataUtils.getExtension(fileName));
    entity.setSize(DataUtils.convertFileSize(fileSize));
    fileStorageService.save(entity);
  }

}
