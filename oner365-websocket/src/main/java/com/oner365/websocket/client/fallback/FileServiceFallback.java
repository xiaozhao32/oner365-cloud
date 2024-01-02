package com.oner365.websocket.client.fallback;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.commons.reponse.ResponseResult;
import com.oner365.websocket.client.IFileServiceClient;

/**
 * 文件服务调用
 * @author liutao
 *
 */
@Component
public class FileServiceFallback implements IFileServiceClient {

	@Override
	public ResponseData<ResponseResult<String>> upload(MultipartFile multipartFile) {
		return null;
	}

}
