package com.oner365.sys.controller.file;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.common.constants.PublicConstants;
import com.oner365.controller.BaseController;
import com.oner365.sys.client.IFileServiceClient;

/**
 * 文件处理
 * 
 * @author zhaoyong
 */
@RefreshScope
@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

  @Autowired
  private IFileServiceClient fileServiceClient;

  /**
   * 上传文件 需要指定 类型: Content-Type: multipart/form-data 方式: Post 参数: @RequestPart
   * 
   * @param multipartFile 文件
   * @return ResponseData
   */
  @PostMapping(value = "/upload")
  public ResponseData<ResponseResult<String>> uploadFile(@RequestBody MultipartFile multipartFile, String dictory) {
    return fileServiceClient.uploadFile(multipartFile, dictory);
  }

  /**
   * 下载
   * 
   * @param fileUrl 文件地址
   * @return byte[] 字节流
   */
  @GetMapping("/byte/download")
  public byte[] download(@RequestParam("fileUrl") String fileUrl) {
    return fileServiceClient.download(fileUrl);
  }

  /**
   * 文件下载
   * 
   * @param fileUrl
   * @return
   */
  @GetMapping("/download")
  public void download(@RequestParam("fileUrl") String fileUrl, HttpServletResponse response) {
    String filename = fileUrl;
    boolean isFilename = StringUtils.contains(filename, PublicConstants.DELIMITER);
    if (isFilename) {
      filename = StringUtils.substringAfterLast(fileUrl, PublicConstants.DELIMITER);
    }
    byte[] data = fileServiceClient.download(fileUrl);
    try {
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());
      response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
          "attachment;filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8.name()));
    } catch (UnsupportedEncodingException e) {
      logger.error("download UnsupportedEncodingException:", e);
    }

    // 写出
    try (ServletOutputStream outputStream = response.getOutputStream()) {
      IOUtils.write(data, outputStream);
    } catch (IOException e) {
      logger.error("download IOException:", e);
    }
  }

}
