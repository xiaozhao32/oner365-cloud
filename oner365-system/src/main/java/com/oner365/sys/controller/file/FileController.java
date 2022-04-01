package com.oner365.sys.controller.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
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
   * @param fileName 文件名称
   * @return ResponseData
   */
  @GetMapping("/download")
  public ResponseData<byte[]> download(String fileName) {
    return ResponseData.success(fileServiceClient.download(fileName));
  }

}
