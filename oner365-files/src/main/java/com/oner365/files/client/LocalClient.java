package com.oner365.files.client;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.common.enums.StorageEnum;
import com.oner365.files.entity.SysFileStorage;
import com.oner365.files.service.IFileStorageService;
import com.oner365.files.storage.IFileStorageClient;
import com.oner365.files.storage.condition.LocalStorageCondition;
import com.oner365.files.util.FileLocalUploadUtils;
import com.oner365.util.DataUtils;

/**
 * 本地上传工具类
 * 
 * @author zhaoyong
 */
@Component
@Conditional(LocalStorageCondition.class)
public class LocalClient implements IFileStorageClient {

    private final Logger logger = LoggerFactory.getLogger(LocalClient.class);

    @Value("${file.local.web:''}")
    private String fileWeb;

    @Value("${file.local.upload:''}")
    private String filePath;

    @Autowired
    private IFileStorageService fileStorageService;

    @Override
    public String uploadFile(MultipartFile file, String dictory) {
        try {
            SysFileStorage fileStorage = FileLocalUploadUtils.upload(file, getName(), fileWeb, filePath, dictory, file.getSize() + 1);
            fileStorageService.save(fileStorage);
            return fileStorage.getFilePath();
        } catch (Exception e) {
            logger.error("upload MultipartFile IOException:", e);
        }
        return null;
    }

    @Override
    public String uploadFile(File file, String dictory) {
        try {
            MultipartFile multipartFile = DataUtils.convertMultipartFile(file);
            SysFileStorage fileStorage = FileLocalUploadUtils.upload(multipartFile, getName(), fileWeb, filePath, dictory,
                    file.length() + 1);
            fileStorageService.save(fileStorage);
            return fileStorage.getFilePath();
        } catch (Exception e) {
            logger.error("upload MultipartFile IOException:", e);
        }
        return null;
    }

    @Override
    public byte[] download(String fileUrl) {
        return FileLocalUploadUtils.download(filePath, fileUrl);
    }

    @Override
    public void deleteFile(String id) {
        FileLocalUploadUtils.delete(filePath, id);
        fileStorageService.deleteById(id);
    }

    @Override
    public StorageEnum getName() {
        return StorageEnum.LOCAL;
    }

}
