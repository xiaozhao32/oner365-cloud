package com.oner365.files.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.oner365.files.config.properties.FileMinioProperties;
import com.oner365.files.storage.condition.MinioStorageCondition;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;

/**
 * File Minio Config
 * 
 * @author zhaoyong
 */
@Configuration
@Conditional(MinioStorageCondition.class)
@EnableConfigurationProperties({ FileMinioProperties.class })
public class FileMinioConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileMinioConfig.class);

  @Autowired
  private FileMinioProperties minioProperties;

  @Bean
  public MinioClient minioClient() {
    try {
      MinioClient minioClient = MinioClient.builder().endpoint(minioProperties.getUrl())
          .credentials(minioProperties.getUsername(), minioProperties.getPassword()).build();

      // 创建根文件夹 bucket
      BucketExistsArgs bucket = (BucketExistsArgs) BucketExistsArgs.builder().bucket(minioProperties.getBucket())
          .build();
      if (!minioClient.bucketExists(bucket)) {
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucket()).build());
      }
      return minioClient;
    } catch (Exception e) {
      LOGGER.error("File Minio Client Error:", e);
    }
    return null;
  }
}
