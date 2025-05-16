package com.oner365.hadoop.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.hadoop.fs.BlockLocation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.hadoop.dto.FileInfoDto;
import com.oner365.hadoop.entity.User;
import com.oner365.hadoop.service.HadoopHdfsService;

/**
 * Hadoop控制器
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/hdfs")
public class HadoopHdfsController {

  @Resource
  private HadoopHdfsService service;

  /**
   * 创建文件夹
   *
   * @param path 文件夹
   * @return ResponseData
   */
  @GetMapping("/mkdir")
  public ResponseData<Boolean> mkdir(@RequestParam String path) {
    boolean result = service.mkdir(path);
    return ResponseData.success(result);
  }

  /**
   * 读取HDFS目录信息
   *
   * @param path 文件
   * @return ResponseData
   */
  @GetMapping("/info")
  public List<FileInfoDto> readPathInfo(@RequestParam String path) {
    return service.readPathInfo(path);
  }

  /**
   * 获取HDFS文件在集群中的位置
   *
   * @param path 文件
   * @return ResponseData
   */
  @GetMapping("/locations")
  public ResponseData<BlockLocation[]> getFileBlockLocations(@RequestParam String path) {
    BlockLocation[] result = service.getFileBlockLocations(path);
    return ResponseData.success(result);
  }

  /**
   * 创建文件
   *
   * @param path 文件
   * @param file 文件
   * @return ResponseData
   */
  @PostMapping("/create")
  public ResponseData<Boolean> createFile(@RequestParam String path, @RequestParam MultipartFile file) {
    service.createFile(path, file);
    return ResponseData.success(Boolean.TRUE);
  }

  /**
   * 读取HDFS文件内容
   *
   * @param path 文件
   * @return ResponseData
   */
  @GetMapping("/read")
  public ResponseData<String> readFile(@RequestParam String path) {
    String result = service.readFile(path);
    return ResponseData.success(result);
  }

  /**
   * 读取HDFS文件转换成Byte类型
   *
   * @param path 文件
   * @return ResponseData
   */
  @GetMapping("/bytes")
  public ResponseData<byte[]> openFileToBytes(@RequestParam String path) {
    byte[] result = service.openFileToBytes(path);
    return ResponseData.success(result);
  }

  /**
   * 读取HDFS文件转换成User对象
   *
   * @param path 文件
   * @return ResponseData
   */
  @GetMapping("/user")
  public User openFileToUser(@RequestParam String path) {
    return service.openFileToObject(path, User.class);
  }

  /**
   * 读取文件列表
   *
   * @param path 文件
   * @return ResponseData
   */
  @GetMapping("/list")
  public List<FileInfoDto> listFile(@RequestParam String path) {
    return service.listFile(path);
  }

  /**
   * 重命名文件
   *
   * @param oldName 源文件
   * @param newName 重命名文件
   * @return ResponseData
   */
  @GetMapping("/rename")
  public ResponseData<Boolean> renameFile(@RequestParam String oldName,
      @RequestParam String newName) {
    boolean result = service.renameFile(oldName, newName);
    return ResponseData.success(result);
  }

  /**
   * 删除文件
   *
   * @param path 文件
   * @return ResponseData
   */
  @DeleteMapping("/delete")
  public ResponseData<Boolean> deleteFile(@RequestParam String path) {
    boolean result = service.deleteFile(path);
    return ResponseData.success(result);
  }

  /**
   * 上传文件
   *
   * @param path       文件
   * @param uploadPath 上传目录
   * @return ResponseData
   */
  @PostMapping("/upload")
  public ResponseData<Boolean> uploadFile(@RequestParam String path,
      @RequestParam String uploadPath) {
    service.uploadFile(path, uploadPath);
    return ResponseData.success(Boolean.TRUE);
  }

  /**
   * 下载文件
   *
   * @param path         文件
   * @param downloadPath 下载目录
   * @return ResponseData
   */
  @PostMapping("/download")
  public ResponseData<Boolean> downloadFile(@RequestParam String path,
      @RequestParam String downloadPath) {
    service.downloadFile(path, downloadPath);
    return ResponseData.success(Boolean.TRUE);
  }

  /**
   * HDFS文件复制
   *
   * @param sourcePath 源文件
   * @param targetPath 目标文件
   * @return ResponseData
   */
  @GetMapping("/copy")
  public ResponseData<Boolean> copyFile(@RequestParam String sourcePath,
      @RequestParam String targetPath) {
    service.copyFile(sourcePath, targetPath);
    return ResponseData.success(Boolean.TRUE);
  }

  /**
   * 查看文件是否已存在
   *
   * @param path 文件
   * @return ResponseData
   */
  @GetMapping("/exist")
  public ResponseData<Boolean> existFile(@RequestParam String path) {
    boolean result = service.existFile(path);
    return ResponseData.success(result);
  }
}
