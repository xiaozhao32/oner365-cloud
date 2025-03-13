package com.oner365.websocket.client.fallback;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.websocket.client.IFileServiceClient;

/**
 * 文件服务调用
 * @author liutao
 *
 */
@Component
public class FileServiceFallback implements IFileServiceClient {

	@Override
	public String upload(MultipartFile multipartFile) {
		return null;
	}

}
