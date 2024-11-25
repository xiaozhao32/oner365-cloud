package com.oner365.hadoop.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.oner365.hadoop.dto.FileInfoDto;
import com.oner365.hadoop.service.HadoopHdfsService;

/**
 * Hadoop服务实现类
 * 
 * @author zhaoyong
 */
@Service
public class HadoopHdfsServiceImpl implements HadoopHdfsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(HadoopHdfsServiceImpl.class);

  private static final int BUFFER_SIZE = 1024 * 1024 * 64;

  @Resource
  private FileSystem fileSystem;

  @Override
  public boolean mkdir(String path) {
    try {
      return fileSystem.mkdirs(new Path(path));
    } catch (IOException e) {
      LOGGER.error("mkdir error:", e);
    }
    return false;
  }

  @Override
  public boolean existFile(String path) {
    if (StringUtils.isEmpty(path)) {
      return false;
    }
    try {
      return fileSystem.exists(new Path(path));
    } catch (IOException e) {
      LOGGER.error("existFile error:", e);
    }
    return false;
  }

  @Override
  public List<FileInfoDto> readPathInfo(String path) {
    try {
      FileStatus[] statusList = fileSystem.listStatus(new Path(path));

      return IntStream.range(0, statusList.length).mapToObj(i -> statusList[i]).map(fileStatus -> {
        FileInfoDto fileInfoDto = new FileInfoDto();
        fileInfoDto.setFileName(fileStatus.getPath().getName());
        fileInfoDto.setFilePath(fileStatus.getPath().toString());
        fileInfoDto.setSize(fileStatus.getLen());
        fileInfoDto.setIsDirectory(fileStatus.isDirectory());
        return fileInfoDto;
      }).collect(Collectors.toList());

    } catch (IOException e) {
      LOGGER.error("readPathInfo error:", e);
    }
    return Collections.emptyList();
  }

  @Override
  public void createFile(String path, MultipartFile file) {
    if (StringUtils.isEmpty(path) || file == null) {
      return;
    }
    String fileName = file.getOriginalFilename();
    try (FSDataOutputStream outputStream = fileSystem.create(new Path(path + File.separator + fileName))) {
      outputStream.write(file.getBytes());
    } catch (IOException e) {
      LOGGER.error("createFile error:", e);
    }
  }

  @Override
  public String readFile(String path) {
    try (FSDataInputStream inputStream = fileSystem.open(new Path(path))) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()));
      String line;
      StringBuilder sb = new StringBuilder();
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
      return sb.toString();
    } catch (IOException e) {
      LOGGER.error("readFile error:", e);
    }
    return null;
  }

  @Override
  public List<FileInfoDto> listFile(String path) {
    List<FileInfoDto> result = new ArrayList<>();

    try {
      RemoteIterator<LocatedFileStatus> filesList = fileSystem.listFiles(new Path(path), true);
      while (filesList.hasNext()) {
        LocatedFileStatus fileStatus = filesList.next();
        FileInfoDto fileInfoDto = new FileInfoDto();
        fileInfoDto.setFileName(fileStatus.getPath().getName());
        fileInfoDto.setFilePath(fileStatus.getPath().toString());
        fileInfoDto.setSize(fileStatus.getLen());
        fileInfoDto.setIsDirectory(fileStatus.isDirectory());
        result.add(fileInfoDto);
      }
    } catch (IOException e) {
      LOGGER.error("listFile error:", e);
    }

    return result;
  }

  @Override
  public boolean renameFile(String oldName, String newName) {
    if (StringUtils.isEmpty(oldName) || StringUtils.isEmpty(newName)) {
      return false;
    }
    try {
      return fileSystem.rename(new Path(oldName), new Path(newName));
    } catch (IOException e) {
      LOGGER.error("renameFile error:", e);
    }
    return false;
  }

  @Override
  public boolean deleteFile(String path) {
    if (StringUtils.isEmpty(path)) {
      return false;
    }
    try {
      return fileSystem.deleteOnExit(new Path(path));
    } catch (IOException e) {
      LOGGER.error("deleteFile error:", e);
    }
    return false;
  }

  @Override
  public void uploadFile(String path, String uploadPath) {
    if (StringUtils.isEmpty(path) || StringUtils.isEmpty(uploadPath)) {
      return;
    }
    try {
      // 调用文件系统的文件复制方法，第一个参数是否删除原文件true为删除，默认为false
      fileSystem.copyFromLocalFile(false, new Path(path), new Path(uploadPath));
    } catch (IOException e) {
      LOGGER.error("uploadFile error:", e);
    }
  }

  @Override
  public void downloadFile(String path, String downloadPath) {
    if (StringUtils.isEmpty(path) || StringUtils.isEmpty(downloadPath)) {
      return;
    }
    try {
      // 调用文件系统的文件复制方法，第一个参数是否删除原文件true为删除，默认为false
      fileSystem.copyToLocalFile(false, new Path(path), new Path(downloadPath));
    } catch (IOException e) {
      LOGGER.error("downloadFile error:", e);
    }
  }

  @Override
  public void copyFile(String sourcePath, String targetPath) {
    if (StringUtils.isEmpty(sourcePath) || StringUtils.isEmpty(targetPath)) {
      return;
    }
    try (FSDataInputStream inputStream = fileSystem.open(new Path(sourcePath));
        FSDataOutputStream outputStream = fileSystem.create(new Path(targetPath))) {

      IOUtils.copyBytes(inputStream, outputStream, BUFFER_SIZE, false);
    } catch (IOException e) {
      LOGGER.error("copyFile error:", e);
    }
  }

  @Override
  public byte[] openFileToBytes(String path) {
    try (FSDataInputStream inputStream = fileSystem.open(new Path(path))) {
      return IOUtils.readFullyToByteArray(inputStream);
    } catch (IOException e) {
      LOGGER.error("openFileToBytes error:", e);
    }
    return new byte[0];
  }

  @Override
  public <T> T openFileToObject(String path, Class<T> clazz) {
    String fileString = readFile(path);
    return JSON.toJavaObject(JSON.parseObject(fileString), clazz);
  }

  @Override
  public BlockLocation[] getFileBlockLocations(String path) {
    try {
      FileStatus fileStatus = fileSystem.getFileStatus(new Path(path));
      return fileSystem.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
    } catch (IOException e) {
      LOGGER.error("getFileBlockLocations error:", e);
    }
    return new BlockLocation[0];
  }

}
