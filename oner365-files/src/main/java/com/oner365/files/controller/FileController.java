package com.oner365.files.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.enums.StorageEnum;
import com.oner365.data.commons.reponse.ResponseResult;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.commons.util.DateUtil;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.web.controller.BaseController;
import com.oner365.files.dto.SysFileStorageDto;
import com.oner365.files.service.IFileStorageService;
import com.oner365.files.storage.IFileStorageClient;

/**
 * 文件上传
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/storage")
public class FileController extends BaseController {
  
  @Resource
  private IFileStorageClient fileStorageClient;

  @Resource
  private IFileStorageService fileStorageService;

  /**
   * 查询列表
   *
   * @param data 查询参数
   * @return PageInfo<SysFileStorage>
   */
  @PostMapping("/list")
  public PageInfo<SysFileStorageDto> pageList(@RequestBody QueryCriteriaBean data) {
    return fileStorageService.pageList(data);
  }
  
  /**
   * 文件上传
   * 
   * @param file    MultipartFile
   * @param dictory 目录
   * @return ResponseResult<String>
   */
  @PostMapping("/upload")
  public ResponseResult<String> uploadMultipartFile(@RequestBody MultipartFile file,
      @RequestParam(name = "dictory", required = false) String dictory) {
    String targetDictory = dictory;
    if (DataUtils.isEmpty(targetDictory)) {
      targetDictory = DateUtil.getCurrentDate();
    }
    String url = fileStorageClient.uploadFile(file, targetDictory);
    return ResponseResult.success(url);
  }

  /**
   * 文件下载
   * 
   * @param fileUrl  url 开头从组名开始
   * @param filename 文件名称
   * @param response HttpServletResponse
   */
  @GetMapping("/download")
  public void download(@RequestParam("fileUrl") String fileUrl, String filename, HttpServletResponse response) {
    byte[] data = fileStorageClient.download(fileUrl);
    if (data == null) {
      response.setStatus(HttpStatus.SC_NOT_FOUND);
      return;
    }
    if (DataUtils.isEmpty(filename)) {
      filename = StringUtils.substringAfterLast(fileUrl, PublicConstants.DELIMITER);
    }

    // 写出
    try (ServletOutputStream outputStream = response.getOutputStream()) {
      response.setCharacterEncoding(Charset.defaultCharset().name());
      response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
          "attachment;filename=" + URLEncoder.encode(filename, Charset.defaultCharset().name()));
      IOUtils.write(data, outputStream);
    } catch (IOException e) {
      logger.error("download IOException:", e);
    }

  }

  /**
   * 文件下载地址
   *
   * @param path url 开头从组名开始
   * @return 文件下载地址
   */
  @GetMapping("/path")
  public ResponseResult<String> downloadPath(@RequestParam("path") String path) {
    String result = fileStorageClient.downloadPath(path);
    return ResponseResult.success(result);
  }

  /**
   * 删除文件
   * 
   * @param ids 文件地址
   * @return List<Boolean>
   */
  @DeleteMapping("/delete")
  public List<Boolean> delete(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> fileStorageClient.deleteFile(id)).collect(Collectors.toList());
  }

  /**
   * 获取文件存储方式
   * 
   * @return StorageEnum
   */
  @GetMapping("/name")
  public StorageEnum getStorageName() {
    return fileStorageClient.getName();
  }
  
  /**
   * 获取文件信息
   *
   * @param id 主键
   * @return SysFileStorageDto
   */
  @PostMapping("/info/{id}")
  public SysFileStorageDto getFileInfo(@PathVariable String id) {
    return fileStorageClient.getFile(id);
  }

}
