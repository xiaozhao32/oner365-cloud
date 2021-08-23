package com.oner365.sys.client.fallback;

import java.io.File;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.common.ResponseData;
import com.oner365.common.constants.ErrorCodes;
import com.oner365.common.constants.ErrorInfo;
import com.oner365.sys.client.IFileServiceClient;

/**
 * 文件服务回调
 * @author zhaoyong
 */
@Component
public class FileServiceClientFallback implements IFileServiceClient {

    @Override
    public ResponseData<Map<String, Object>> upload(MultipartFile multipartFile) {
        return new ResponseData<>(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
    }

    @Override
    public ResponseData<Map<String, Object>> upload(File file) {
        return new ResponseData<>(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
    }

    @Override
    public ResponseData<String> delete(String... ids) {
        return new ResponseData<>(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
    }

}
