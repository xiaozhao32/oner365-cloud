package com.oner365.swagger.client.hadoop;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.common.ResponseData;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.FileInfoDto;

/**
 * Hadoop服务 - Hdfs
 *
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_HADOOP, contextId = PathConstants.CONTEXT_HADOOP_HDFS_ID)
public interface IHadoopHdfsClient {

  /**
   * 创建文件夹
   *
   * @param path 文件夹
   * @return ResponseData<Boolean>
   */
  @GetMapping(PathConstants.REQUEST_HADOOP_HDFS_MKDIR)
  ResponseData<Boolean> mkdir(@RequestParam("path") String path);

  /**
   * 上传文件
   *
   * @param path 文件
   * @param file 文件
   * @return ResponseData
   */
  @PostMapping(PathConstants.REQUEST_HADOOP_HDFS_CREATE)
  ResponseData<Boolean> createFile(@RequestParam("path") String path, @RequestParam("file") MultipartFile file);

  /**
   * 获取文件
   *
   * @param path 文件
   * @return ResponseData
   */
  @GetMapping(PathConstants.REQUEST_HADOOP_HDFS_BYTES)
  ResponseData<byte[]> openFileToBytes(@RequestParam("path") String path);

  /**
   * 读取文件列表
   *
   * @param path 文件
   * @return ResponseData
   */
  @GetMapping(PathConstants.REQUEST_HADOOP_HDFS_LIST)
  ResponseData<List<FileInfoDto>> listFile(@RequestParam("path") String path);

  /**
   * 删除文件
   *
   * @param path 文件
   * @return ResponseData
   */
  @DeleteMapping(PathConstants.REQUEST_HADOOP_HDFS_DELETE)
  ResponseData<Boolean> deleteFile(@RequestParam("path") String path);
}
