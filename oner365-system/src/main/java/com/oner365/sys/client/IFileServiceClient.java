package com.oner365.sys.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.sys.client.fallback.FileServiceClientFallback;

/**
 * 文件处理接口
 * 
 * @author zhaoyong
 */
@FeignClient(value = "oner365-files", fallback = FileServiceClientFallback.class)
public interface IFileServiceClient {

  /**
   * 上传文件 类型: Content-Type: multipart/form-data 方式: Post 参数: @RequestPart("name")
   *
   * 服务端接收方式: @RequestBody
   * 
   * @param file MultipartFile
   * @return ResponseData
   */
  @PostMapping(value = "/fdfs/uploadMultipartFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  ResponseData<ResponseResult<String>> uploadFile(@RequestPart("file") MultipartFile file,
      @RequestParam("dictory") String dictory);

  /**
   * 删除文件
   * 
   * @param ids 文件id
   * @return ResponseData
   */
  @DeleteMapping("/fdfs/delete")
  ResponseData<String> delete(@RequestBody String... ids);

}
