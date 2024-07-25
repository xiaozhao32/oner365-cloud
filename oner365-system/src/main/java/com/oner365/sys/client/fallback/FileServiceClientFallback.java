package com.oner365.sys.client.fallback;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.data.commons.enums.ErrorInfoEnum;
import com.oner365.data.commons.enums.ResultEnum;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.commons.reponse.ResponseResult;
import com.oner365.sys.client.IFileServiceClient;

/**
 * 文件服务回调
 * 
 * @author zhaoyong
 */
@Component
public class FileServiceClientFallback implements IFileServiceClient {

  @Override
  public ResponseData<ResponseResult<String>> uploadFile(MultipartFile multipartFile, String dictory) {
    return ResponseData.error(ResultEnum.ERROR.getCode(), ErrorInfoEnum.PARAM.getName());
  }
  
  @Override
  public byte[] download(String fileUrl) {
    return new byte[0];
  }

  @Override
  public ResponseData<String> delete(String... ids) {
    return ResponseData.error(ResultEnum.ERROR.getCode(), ErrorInfoEnum.PARAM.getName());
  }

}
