package com.oner365.websocket.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.websocket.client.IFileServiceClient;

import javax.annotation.Resource;

/**
 * 文件处理
 * @author liutao
 */
@RefreshScope
@RestController
@RequestMapping("/file")
public class FileController {

	@Resource
	private IFileServiceClient client;

	/**
	 * 上传文件
	 * 需要指定
	 * 		类型: Content-Type: multipart/form-data
	 * 		方式: Post
	 * 		参数: @RequestPart
	 * @param multipartFile 文件
	 * @return ResponseData
	 */
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseData<ResponseResult<String>> uploadFile(@RequestPart("multipartFile") MultipartFile multipartFile) {
		return client.upload(multipartFile);
	}
}
