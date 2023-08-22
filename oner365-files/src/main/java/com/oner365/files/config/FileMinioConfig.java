package com.oner365.files.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;
import com.oner365.files.config.properties.MinioProperties;
import com.oner365.files.storage.condition.MinioStorageCondition;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;

/**
 * File Minio Config
 * 
 * @author zhaoyong
 */
@Configuration
@Conditional(MinioStorageCondition.class)
@EnableConfigurationProperties({ MinioProperties.class })
public class FileMinioConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileMinioConfig.class);

  @Bean
  MinioClient minioClient(MinioProperties minioProperties) {
    try {
      MinioClient minioClient = MinioClient.builder().endpoint(minioProperties.getUrl())
          .credentials(minioProperties.getUsername(), minioProperties.getPassword()).region("cn-north-1").build();

      // 创建根文件夹 bucket
      BucketExistsArgs bucket = BucketExistsArgs.builder().bucket(minioProperties.getBucket())
          .build();
      if (!minioClient.bucketExists(bucket)) {
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucket()).build());

        String sb = "{\"Statement\": [{\"Action\": [\"s3:GetBucketLocation\",\"s3:ListBucket\"], \"Effect\": \"Allow\",\"Principal\": \"*\",\"Resource\": \"arn:aws:s3:::"
            + minioProperties.getBucket()
            + "\"}, {\"Action\": \"s3:GetObject\", \"Effect\": \"Allow\", \"Principal\": \"*\", \"Resource\": \"arn:aws:s3:::"
            + minioProperties.getBucket() + "/*\"}],\"Version\": \"2012-10-17\"}";
        minioClient
            .setBucketPolicy(SetBucketPolicyArgs.builder().bucket(minioProperties.getBucket()).config(sb).build());
      }
      return minioClient;
    } catch (Exception e) {
      LOGGER.error("File Minio Client Error: {}", JSON.toJSONString(minioProperties));  
      LOGGER.error("File Minio Client Error:", e);
    }
    return null;
  }
}
