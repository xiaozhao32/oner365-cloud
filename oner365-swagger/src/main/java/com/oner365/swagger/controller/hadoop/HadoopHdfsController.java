package com.oner365.swagger.controller.hadoop;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.swagger.client.hadoop.IHadoopHdfsClient;
import com.oner365.swagger.dto.FileInfoDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Hadoop文件处理
 *
 * @author zhaoyong
 *
 */
@RestController
@Api(tags = "Hadoop文件处理")
@RequestMapping("/hadoop/hdfs")
public class HadoopHdfsController {

  @Resource
  private IHadoopHdfsClient client;

  /**
   * 创建文件夹
   *
   * @param path 文件夹
   * @return ResponseData
   */
  @ApiOperation("1.创建文件夹")
  @ApiOperationSupport(order = 1)
  @GetMapping("/mkdir")
  public ResponseData<Boolean> mkdir(@RequestParam("path") String path) {
    return client.mkdir(path);
  }
  
  /**
   * 读取HDFS目录信息
   *
   * @param path 文件
   * @return ResponseData
   */
  @ApiOperation("2.读取目录")
  @ApiOperationSupport(order = 2)
  @GetMapping("/info")
  public ResponseData<List<FileInfoDto>> readPathInfo(@RequestParam("path") String path) {
    return client.readPathInfo(path);
  }

  /**
   * 获取HDFS文件在集群中的位置
   *
   * @param path 文件
   * @return ResponseData
   */
  @ApiOperation("3.获取位置")
  @ApiOperationSupport(order = 3)
  @GetMapping("/locations")
  public ResponseData<Object> getFileBlockLocations(@RequestParam("path") String path) {
    return client.getFileBlockLocations(path);
  }

  /**
   * 创建文件
   *
   * @param path 文件
   * @param file 文件
   * @return ResponseData
   */
  @ApiOperation("4.创建文件")
  @ApiOperationSupport(order = 4)
  @PostMapping("/create")
  public ResponseData<Boolean> createFile(@RequestParam("path") String path, @RequestParam("file") MultipartFile file) {
    return client.createFile(path, file);
  }
  
  /**
   * 读取HDFS文件内容
   *
   * @param path 文件
   * @return ResponseData
   */
  @ApiOperation("5.读取内容")
  @ApiOperationSupport(order = 5)
  @GetMapping("/read")
  public ResponseData<String> readFile(@RequestParam("path") String path) {
    return client.readFile(path);
  }

  /**
   * 获取文件
   *
   * @param path 文件
   * @return ResponseData
   */
  @ApiOperation("6.获取文件")
  @ApiOperationSupport(order = 6)
  @GetMapping("/bytes")
  public ResponseData<byte[]> openFileToBytes(@RequestParam("path") String path) {
    return client.openFileToBytes(path);
  }

  /**
   * 获取文件列表
   *
   * @param path 文件
   * @return ResponseData
   */
  @ApiOperation("7.获取文件列表")
  @ApiOperationSupport(order = 7)
  @GetMapping("/list")
  public ResponseData<List<FileInfoDto>> listFile(@RequestParam("path") String path) {
    return client.listFile(path);
  }

  /**
   * 删除文件
   *
   * @param path 文件
   * @return ResponseData
   */
  @ApiOperation("8.删除文件")
  @ApiOperationSupport(order = 8)
  @DeleteMapping("/delete")
  public ResponseData<Boolean> deleteFile(@RequestParam("path") String path) {
    return client.deleteFile(path);
  }
}
