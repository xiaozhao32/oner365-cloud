package com.oner365.files.storage;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.oner365.common.enums.StorageEnum;

/**
 * 文件存储接口
 * 
 * @author zhaoyong
 */
public interface IFileStorageClient {

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @param dictory 上传目录
     * @return 文件访问地址
     */
    String uploadFile(MultipartFile file, String dictory);

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @param dictory 上传目录
     * @return 文件访问地址
     */
    String uploadFile(File file, String dictory);

    /**
     * 下载文件
     *
     * @param fileUrl 文件url
     * @return byte[]
     */
    byte[] download(String fileUrl);

    /**
     * 删除文件
     *
     * @param fileUrl 文件访问地址
     * @return Boolean
     */
    Boolean deleteFile(String fileUrl);

    /**
     * 获取文件存储方式
     * 
     * @return StorageEnum
     */
    StorageEnum getName();

}
