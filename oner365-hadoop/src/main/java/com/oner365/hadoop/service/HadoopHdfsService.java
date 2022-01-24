package com.oner365.hadoop.service;

import java.util.List;

import org.apache.hadoop.fs.BlockLocation;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.hadoop.dto.FileInfoDto;

/**
 * Hadoop服务接口
 * 
 * @author zhaoyong
 */
public interface HadoopHdfsService {

  /**
   * 在HDFS创建文件夹
   *
   * @param path 文件夹
   * @return boolean
   */
  boolean mkdir(String path);

  /**
   * 判断HDFS文件是否存在
   *
   * @param path 文件
   * @return boolean
   */
  boolean existFile(String path);

  /**
   * 读取HDFS目录信息
   *
   * @param path 文件
   * @return List<FileInfoDto>
   */
  List<FileInfoDto> readPathInfo(String path);

  /**
   * HDFS创建文件
   *
   * @param path 文件
   * @param file 文件
   */
  void createFile(String path, MultipartFile file);

  /**
   * 读取HDFS文件内容
   *
   * @param path 文件
   * @return String
   */
  String readFile(String path);

  /**
   * 读取HDFS文件列表
   *
   * @param path 文件
   * @return List
   */
  List<FileInfoDto> listFile(String path);

  /**
   * HDFS重命名文件
   *
   * @param oldName 源文件
   * @param newName 重命名文件
   * @return boolean
   */
  boolean renameFile(String oldName, String newName);

  /**
   * 删除HDFS文件
   *
   * @param path 文件
   * @return boolean
   */
  boolean deleteFile(String path);

  /**
   * 上传HDFS文件
   *
   * @param path       文件
   * @param uploadPath 上传文件
   */
  void uploadFile(String path, String uploadPath);

  /**
   * 下载HDFS文件
   *
   * @param path         文件
   * @param downloadPath 下载文件
   */
  void downloadFile(String path, String downloadPath);

  /**
   * HDFS文件复制
   *
   * @param sourcePath 源文件
   * @param targetPath 目标文件
   */
  void copyFile(String sourcePath, String targetPath);

  /**
   * 打开HDFS上的文件并返回byte数组
   *
   * @param path 文件
   * @return byte[]
   */
  byte[] openFileToBytes(String path);

  /**
   * 打开HDFS上的文件并返回java对象
   *
   * @param path 文件
   * @return Class<T>
   */
  <T> T openFileToObject(String path, Class<T> clazz);

  /**
   * 获取某个文件在HDFS的集群位置
   *
   * @param path 文件
   * @return BlockLocation
   */
  BlockLocation[] getFileBlockLocations(String path);

}
