package com.oner365.swagger.controller.files;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.commons.util.DateUtil;
import com.oner365.swagger.client.files.IFilesStorageClient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 文件中心 - 文件管理
 *
 * @author zhaoyong
 */
@RestController
@Api(tags = "文件中心")
@RequestMapping("/file")
public class FileController {
  
  private final Logger logger = LoggerFactory.getLogger(FileController.class);

  @Resource
  private IFilesStorageClient client;

  /**
   * 文件上传
   *
   * @param file    MultipartFile
   * @param dictory 目录
   * @return String
   */
  @ApiOperation("1.文件上传")
  @ApiOperationSupport(order = 1)
  @PostMapping("/upload")
  public String upload(
      @ApiParam(name = "file", value = "文件") @RequestPart("file") MultipartFile file,
      @ApiParam(name = "dictory", value = "上传目录") @RequestParam(required = false) String dictory) {
    String targetDictory = null;
    if (DataUtils.isEmpty(dictory)) {
      targetDictory = DateUtil.getCurrentDate();
    }
    return client.upload(file, targetDictory);
  }

  /**
   * 文件下载
   *
   * @param fileUrl 地址
   * @param response response
   */
  @ApiOperation("2.文件下载")
  @ApiOperationSupport(order = 2)
  @GetMapping("/download")
  public void download(@RequestParam String fileUrl, HttpServletResponse response) {
    String filename = fileUrl;
    boolean isFilename = StringUtils.contains(filename, PublicConstants.DELIMITER);
    if (isFilename) {
      filename = StringUtils.substringAfterLast(fileUrl, PublicConstants.DELIMITER);
    }

    // 写出
    try (ServletOutputStream outputStream = response.getOutputStream()) {
      byte[] data = client.download(fileUrl);
      response.setCharacterEncoding(Charset.defaultCharset().name());
      response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
          "attachment;filename=" + URLEncoder.encode(filename, Charset.defaultCharset().name()));
      IOUtils.write(data, outputStream);
    } catch (IOException e) {
      logger.error("download IOException:", e);
    }
  }
}
