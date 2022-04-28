package com.oner365.swagger.controller.files;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.common.constants.PublicConstants;
import com.oner365.controller.BaseController;
import com.oner365.swagger.client.files.IFilesStorageClient;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;

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
public class FileController extends BaseController {

  @Autowired
  private IFilesStorageClient client;

  /**
   * 文件上传
   * 
   * @param file    MultipartFile
   * @param dictory 目录
   * @return ResponseResult<String>
   */
  @ApiOperation("1.文件上传")
  @ApiOperationSupport(order = 1)
  @PostMapping("/upload")
  public ResponseData<ResponseResult<String>> upload(
      @ApiParam(name = "file", value = "文件") @RequestPart("file") MultipartFile file,
      @ApiParam(name = "dictory", value = "上传目录") @RequestParam(name = "dictory", required = false) String dictory) {
    String targetDictory = null;
    if (DataUtils.isEmpty(dictory)) {
      targetDictory = DateUtil.getCurrentDate();
    }
    return client.upload(file, targetDictory);
  }
  
  /**
   * 文件下载
   * @param fileUrl
   * @return
   */
  @ApiOperation("2.文件下载")
  @ApiOperationSupport(order = 2)
  @GetMapping("/download")
  public void download(@RequestParam("fileUrl") String fileUrl, HttpServletResponse response) {
    String filename = fileUrl;
    boolean isFilename = StringUtils.contains(filename, PublicConstants.DELIMITER);
    if (isFilename) {
      filename = StringUtils.substringAfterLast(fileUrl, PublicConstants.DELIMITER);
    }
    byte[] data = client.download(fileUrl);
    response.setCharacterEncoding(Charset.defaultCharset().name());
    response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
        "attachment;filename=" + URLEncoder.encode(filename, Charset.defaultCharset()));

    // 写出
    try (ServletOutputStream outputStream = response.getOutputStream()) {
      IOUtils.write(data, outputStream);
    } catch (IOException e) {
      logger.error("download IOException:", e);
    }
  }
}
