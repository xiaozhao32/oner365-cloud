package com.oner365.files.storage;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.oner365.common.enums.StorageEnum;
import com.oner365.files.dto.SysFileStorageDto;

/**
 * 文件存储接口
 *
 * @author zhaoyong
 */
public interface IFileStorageClient {

  /**
   * 上传文件
   *
   * @param file      文件对象
   * @param directory 本地上传目录
   * @return 文件访问地址
   */
  String uploadFile(MultipartFile file, String directory);

  /**
   * 上传文件
   *
   * @param file      文件对象
   * @param directory 本地上传目录
   * @return 文件访问地址
   */
  String uploadFile(File file, String directory);

  /**
   * 下载文件
   *
   * @param path 文件url
   * @return 文件流 byte[]
   */
  byte[] download(String path);
  
  /**
   * 下载文件
   *
   * @param path 文件url
   * @return 负载下载地址
   */
  String downloadPath(String path);

  /**
   * 获取文件大小
   * 
   * @param path
   * @return Long
   */
  Long getFileSize(String path);

  /**
   * 删除文件
   *
   * @param path 文件访问地址
   * @return Boolean
   */
  Boolean deleteFile(String path);

  /**
   * 获取文件存储方式
   *
   * @return StorageEnum
   */
  StorageEnum getName();

  /**
   * 获取文件
   * 
   * @param id 主键
   * @return SysFileStorageDto
   */
  SysFileStorageDto getFile(String id);

}
