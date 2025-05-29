package com.oner365.files.client;

import java.io.File;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.enums.StorageEnum;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.web.sequence.sequence.SnowflakeSequence;
import com.oner365.data.web.utils.HttpClientUtils;
import com.oner365.files.config.properties.FileLocalProperties;
import com.oner365.files.dto.SysFileStorageDto;
import com.oner365.files.service.IFileStorageService;
import com.oner365.files.storage.IFileStorageClient;
import com.oner365.files.storage.condition.LocalStorageCondition;
import com.oner365.files.util.FileLocalUploadUtils;
import com.oner365.files.vo.SysFileStorageVo;

/**
 * 本地上传工具类
 *
 * @author zhaoyong
 */
@Component
@Conditional(LocalStorageCondition.class)
public class FileLocalClient implements IFileStorageClient {

    private final Logger logger = LoggerFactory.getLogger(FileLocalClient.class);

    @Resource
    private FileLocalProperties fileLocalProperties;

    @Resource
    private IFileStorageService fileStorageService;

    @Resource
    private SnowflakeSequence snowflakeSequence;

    @Override
    public String uploadFile(MultipartFile file, String directory) {
        try {
            SysFileStorageVo entity = FileLocalUploadUtils.upload(file, getName(), snowflakeSequence.nextNo(),
                    fileLocalProperties.getWeb(), fileLocalProperties.getUpload(), directory, file.getSize() + 1);
            SysFileStorageDto result = fileStorageService.save(entity);
            if (result != null) {
                return result.getId();
            }
        }
        catch (Exception e) {
            logger.error("upload MultipartFile IOException:", e);
        }
        return null;
    }

    @Override
    public String uploadFile(File file, String directory) {
        try {
            MultipartFile multipartFile = HttpClientUtils.convertMultipartFile(file);
            SysFileStorageVo entity = FileLocalUploadUtils.upload(multipartFile, getName(), snowflakeSequence.nextNo(),
                    fileLocalProperties.getWeb(), fileLocalProperties.getUpload(), directory, file.length() + 1);
            SysFileStorageDto result = fileStorageService.save(entity);
            if (result != null) {
                return result.getId();
            }
        }
        catch (Exception e) {
            logger.error("upload MultipartFile IOException:", e);
        }
        return null;
    }

    @Override
    public byte[] download(String path) {
        return FileLocalUploadUtils.download(fileLocalProperties.getUpload(), path);
    }

    @Override
    public String downloadPath(String path) {
        String result = fileLocalProperties.getWeb() + PublicConstants.DELIMITER + path;
        logger.info("file download: {}", result);
        return result;
    }

    @Override
    public Long getFileSize(String path) {
        String filePath = fileLocalProperties.getUpload() + PublicConstants.DELIMITER + path;
        File file = DataUtils.getFile(filePath);
        if (!file.exists()) {
            logger.error("file is not exists: {}", filePath);
            return 0L;
        }
        return file.length();
    }

    @Override
    public Boolean deleteFile(String id) {
        return fileStorageService.deleteById(id);
    }

    @Override
    public StorageEnum getName() {
        return StorageEnum.LOCAL;
    }

    @Override
    public SysFileStorageDto getFile(String id) {
        return fileStorageService.getById(id);
    }

}
