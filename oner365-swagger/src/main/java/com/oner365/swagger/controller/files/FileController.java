package com.oner365.swagger.controller.files;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.oner365.controller.BaseController;
import com.oner365.swagger.client.files.IFilesStorageClient;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 系统监控 - 缓存管理
 * 
 * @author zhaoyong
 */
@RestController
@Api(tags = "缓存管理")
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
  public ResponseData<byte[]> download(@RequestParam("fileUrl") String fileUrl) {
    return client.download(fileUrl);
  }
}
