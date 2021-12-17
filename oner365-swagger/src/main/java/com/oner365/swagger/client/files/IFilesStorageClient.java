package com.oner365.swagger.client.files;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.swagger.constants.PathConstants;

/**
 * 文件服务 - 存储
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_FILES, contextId = PathConstants.CONTEXT_FILES_STORAGE_ID)
public interface IFilesStorageClient {

  /**
   * 文件上传
   * 
   * @param file 文件
   * @param dictory 上传目录
   * @return ResponseData<ResponseResult<String>>
   */
  @PostMapping(value = PathConstants.REQUEST_FILES_STORAGE_UPLOAD, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  ResponseData<ResponseResult<String>> upload(@RequestPart("file") MultipartFile file,
      @RequestParam(name = "dictory", required = false) String dictory);
  
  /**
   * 文件流下载 如直接下载需访问文件地址
   * 
   * @param fileUrl 文件路径
   * @return ResponseData<byte[]>
   */
  @GetMapping(PathConstants.REQUEST_FILES_STORAGE_DOWNLOAD)
  ResponseData<byte[]> download(@RequestParam("fileUrl") String fileUrl);
}
