package com.oner365.files.client;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.common.enums.StorageEnum;
import com.oner365.common.sequence.sequence.SnowflakeSequence;
import com.oner365.files.config.properties.FileLocalProperties;
import com.oner365.files.dto.SysFileStorageDto;
import com.oner365.files.service.IFileStorageService;
import com.oner365.files.storage.IFileStorageClient;
import com.oner365.files.storage.condition.LocalStorageCondition;
import com.oner365.files.util.FileLocalUploadUtils;
import com.oner365.files.vo.SysFileStorageVo;
import com.oner365.util.DataUtils;

/**
 * 本地上传工具类
 *
 * @author zhaoyong
 */
@Component
@Conditional(LocalStorageCondition.class)
public class FileLocalClient implements IFileStorageClient {

  private final Logger logger = LoggerFactory.getLogger(FileLocalClient.class);

  @Autowired
  private FileLocalProperties fileLocalProperties;

  @Autowired
  private IFileStorageService fileStorageService;

  @Autowired
  private SnowflakeSequence snowflakeSequence;

  @Override
  public String uploadFile(MultipartFile file, String directory) {
    try {
      SysFileStorageVo entity = FileLocalUploadUtils.upload(file, getName(), snowflakeSequence.nextNo(),
          fileLocalProperties.getWeb(), fileLocalProperties.getUpload(), directory, file.getSize() + 1);
      SysFileStorageDto result = fileStorageService.save(entity);
      return result.getFilePath();
    } catch (Exception e) {
      logger.error("upload MultipartFile IOException:", e);
    }
    return null;
  }

  @Override
  public String uploadFile(File file, String directory) {
    try {
      MultipartFile multipartFile = DataUtils.convertMultipartFile(file);
      SysFileStorageVo entity = FileLocalUploadUtils.upload(multipartFile, getName(), snowflakeSequence.nextNo(),
          fileLocalProperties.getWeb(), fileLocalProperties.getUpload(), directory, file.length() + 1);
      SysFileStorageDto result = fileStorageService.save(entity);
      return result.getFilePath();
    } catch (Exception e) {
      logger.error("upload MultipartFile IOException:", e);
    }
    return null;
  }

  @Override
  public byte[] download(String fileUrl) {
    return FileLocalUploadUtils.download(fileLocalProperties.getUpload(), fileUrl);
  }

  @Override
  public Boolean deleteFile(String id) {
    return fileStorageService.deleteById(id);
  }

  @Override
  public StorageEnum getName() {
    return StorageEnum.LOCAL;
  }

}
