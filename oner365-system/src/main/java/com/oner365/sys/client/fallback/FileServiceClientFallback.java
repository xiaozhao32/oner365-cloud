package com.oner365.sys.client.fallback;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.data.commons.enums.ErrorInfoEnum;
import com.oner365.sys.client.IFileServiceClient;

/**
 * 文件服务回调
 * 
 * @author zhaoyong
 */
@Component
public class FileServiceClientFallback implements IFileServiceClient {

  @Override
  public String uploadFile(MultipartFile multipartFile, String dictory) {
    return ErrorInfoEnum.PARAM.getName();
  }
  
  @Override
  public byte[] download(String fileUrl) {
    return new byte[0];
  }

  @Override
  public List<Boolean> delete(String... ids) {
    return Collections.singletonList(Boolean.FALSE);
  }

}
