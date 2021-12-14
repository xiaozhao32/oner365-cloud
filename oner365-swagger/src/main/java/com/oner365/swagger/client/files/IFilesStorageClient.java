package com.oner365.swagger.client.files;

import org.springframework.cloud.openfeign.FeignClient;

import com.oner365.swagger.constants.PathConstants;

/**
 * 文件服务 - 存储
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_FILES, contextId = PathConstants.CONTEXT_FILES_STORAGE_ID)
public interface IFilesStorageClient {

}
