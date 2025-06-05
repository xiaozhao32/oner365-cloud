package com.oner365.websocket.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.websocket.client.fallback.FileServiceFallback;

/**
 * 文件服务调用
 *
 * @author liutao
 *
 */
@FeignClient(value = "oner365-files", fallback = FileServiceFallback.class)
public interface IFileServiceClient {

    /**
     * 上传文件 类型: Content-Type: multipart/form-data 方式: Post 参数: @RequestPart("name")
     *
     * 服务端接收方式: @RequestBody
     * @param file 文件
     * @return String 文件路径
     */
    @PostMapping(value = "/storage/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String upload(@RequestPart("file") MultipartFile file);

}
