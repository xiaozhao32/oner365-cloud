package com.oner365.hadoop.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.oner365.hadoop.config.properties.HdfsProperties;

/**
 * 文件上传服务
 * 
 * @author zhaoyong
 */
@Component
public class HdfsService {

  private static String hdfsPath;
  private static String hdfsName;
  private static final int BUFFER_SIZE = 1024 * 1024 * 64;

  @Autowired
  private HdfsProperties hdfsProperties;

  /**
   * 获取HDFS配置信息
   *
   * @return Configuration
   */
  private static Configuration getConfiguration() {
    Configuration configuration = new Configuration();
    configuration.set("fs.defaultFS", hdfsPath);
    return configuration;
  }

  /**
   * 获取HDFS文件系统对象
   *
   * @return FileSystem
   * @throws IOException          异常
   * @throws InterruptedException 异常
   * @throws URISyntaxException   异常
   */
  public static FileSystem getFileSystem() throws IOException, InterruptedException, URISyntaxException {
    // 客户端去操作hdfs时是有一个用户身份的，默认情况下hdfs客户端api会从jvm中获取一个参数作为自己的用户身份
    // HADOOP_USER_NAME=hadoop
    // 也可以在构造客户端fs对象时，通过参数传递进去
    return FileSystem.get(new URI(hdfsPath), getConfiguration(), hdfsName);
  }

  /**
   * 在HDFS创建文件夹
   *
   * @param path 文件夹
   * @return boolean
   * @throws IOException 异常
   */
  public static boolean mkdir(FileSystem fs, String path) throws IOException {
    return fs.mkdirs(new Path(path));
  }

  /**
   * 判断HDFS文件是否存在
   *
   * @param path 文件
   * @return boolean
   * @throws IOException 异常
   */
  public static boolean existFile(FileSystem fs, String path) throws IOException {
    if (StringUtils.isEmpty(path)) {
      return false;
    }

    return fs.exists(new Path(path));
  }

  /**
   * 读取HDFS目录信息
   *
   * @param path 文件
   * @return List<Map<String, Object>>
   * @throws IOException 异常
   */
  public static List<Map<String, Object>> readPathInfo(FileSystem fs, String path) throws IOException {
    List<Map<String, Object>> result = new ArrayList<>();
    FileStatus[] statusList = fs.listStatus(new Path(path));
    for (FileStatus fileStatus : statusList) {
      Map<String, Object> map = new HashMap<>(2);
      map.put("filePath", fileStatus.getPath());
      map.put("fileStatus", fileStatus.toString());
      result.add(map);
    }
    return result;
  }

  /**
   * HDFS创建文件
   *
   * @param path 文件
   * @param file 文件
   * @throws IOException 异常
   */
  public static void createFile(FileSystem fs, String path, MultipartFile file) throws IOException {
    if (StringUtils.isEmpty(path) || file == null) {
      return;
    }
    String fileName = file.getOriginalFilename();
    try (FSDataOutputStream outputStream = fs.create(new Path(path + File.separator + fileName))) {
      outputStream.write(file.getBytes());
    }
  }

  /**
   * 读取HDFS文件内容
   *
   * @param path 文件
   * @return String
   * @throws IOException 异常
   */
  public static String readFile(FileSystem fs, String path) throws IOException {
    try (FSDataInputStream inputStream = fs.open(new Path(path))) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()));
      String line;
      StringBuilder sb = new StringBuilder();
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
      return sb.toString();
    }
  }

  /**
   * 读取HDFS文件列表
   *
   * @param path 文件
   * @return List
   * @throws IOException 异常
   */
  public static List<Map<String, String>> listFile(FileSystem fs, String path) throws IOException {
    List<Map<String, String>> result = new ArrayList<>();

    RemoteIterator<LocatedFileStatus> filesList = fs.listFiles(new Path(path), true);
    while (filesList.hasNext()) {
      LocatedFileStatus fileStatus = filesList.next();
      Map<String, String> map = new HashMap<>(2);
      map.put("fileName", fileStatus.getPath().getName());
      map.put("filePath", fileStatus.getPath().toString());
      result.add(map);
    }

    return result;
  }

  /**
   * HDFS重命名文件
   *
   * @param oldName 源文件
   * @param newName 重命名文件
   * @return boolean
   * @throws IOException 异常
   */
  public static boolean renameFile(FileSystem fs, String oldName, String newName) throws IOException {
    if (StringUtils.isEmpty(oldName) || StringUtils.isEmpty(newName)) {
      return false;
    }
    return fs.rename(new Path(oldName), new Path(newName));
  }

  /**
   * 删除HDFS文件
   *
   * @param path 文件
   * @return boolean
   * @throws IOException 异常
   */
  public static boolean deleteFile(FileSystem fs, String path) throws IOException {
    if (StringUtils.isEmpty(path)) {
      return false;
    }
    return fs.deleteOnExit(new Path(path));
  }

  /**
   * 上传HDFS文件
   *
   * @param path       文件
   * @param uploadPath 上传文件
   * @throws IOException 异常
   */
  public static void uploadFile(FileSystem fs, String path, String uploadPath) throws IOException {
    if (StringUtils.isEmpty(path) || StringUtils.isEmpty(uploadPath)) {
      return;
    }
    // 调用文件系统的文件复制方法，第一个参数是否删除原文件true为删除，默认为false
    fs.copyFromLocalFile(false, new Path(path), new Path(uploadPath));
  }

  /**
   * 下载HDFS文件
   *
   * @param path         文件
   * @param downloadPath 下载文件
   * @throws IOException 异常
   */
  public static void downloadFile(FileSystem fs, String path, String downloadPath) throws IOException {
    if (StringUtils.isEmpty(path) || StringUtils.isEmpty(downloadPath)) {
      return;
    }
    // 调用文件系统的文件复制方法，第一个参数是否删除原文件true为删除，默认为false
    fs.copyToLocalFile(false, new Path(path), new Path(downloadPath));
  }

  /**
   * HDFS文件复制
   *
   * @param sourcePath 源文件
   * @param targetPath 目标文件
   * @throws IOException 异常
   */
  public static void copyFile(FileSystem fs, String sourcePath, String targetPath) throws IOException {
    if (StringUtils.isEmpty(sourcePath) || StringUtils.isEmpty(targetPath)) {
      return;
    }
    try (FSDataInputStream inputStream = fs.open(new Path(sourcePath));
        FSDataOutputStream outputStream = fs.create(new Path(targetPath))) {

      IOUtils.copyBytes(inputStream, outputStream, BUFFER_SIZE, false);
    }
  }

  /**
   * 打开HDFS上的文件并返回byte数组
   *
   * @param path 文件
   * @return byte[]
   * @throws IOException 异常
   */
  public static byte[] openFileToBytes(FileSystem fs, String path) throws IOException {
    try (FSDataInputStream inputStream = fs.open(new Path(path))) {
      return IOUtils.readFullyToByteArray(inputStream);
    }
  }

  /**
   * 打开HDFS上的文件并返回java对象
   *
   * @param path 文件
   * @return Class<T>
   * @throws IOException 异常
   */
  public static <T> T openFileToObject(FileSystem fs, String path, Class<T> clazz) throws IOException {
    String fileString = readFile(fs, path);
    return JSON.toJavaObject(JSON.parseObject(fileString), clazz);
  }

  /**
   * 获取某个文件在HDFS的集群位置
   *
   * @param path 文件
   * @return BlockLocation
   * @throws IOException 异常
   */
  public static BlockLocation[] getFileBlockLocations(FileSystem fs, String path) throws IOException {
    FileStatus fileStatus = fs.getFileStatus(new Path(path));
    return fs.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
  }

  @PostConstruct
  public void getPath() {
    hdfsPath = hdfsProperties.getPath();
  }

  @PostConstruct
  public void getName() {
    hdfsName = hdfsProperties.getUsername();
  }

}
