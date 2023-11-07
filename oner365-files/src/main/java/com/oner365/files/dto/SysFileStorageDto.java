package com.oner365.files.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.oner365.common.enums.StorageEnum;

/**
 * 文件对象
 * @author zhaoyong
 *
 */
public class SysFileStorageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 id
     */
    private String id;

    /**
     * 文件名称 file_name
     */
    private String fileName;

    /**
     * 显示名称 display_name
     */
    private String displayName;
    
    /**
     * 存储方式
     */
    private StorageEnum fileStorage;

    /**
     * 文件路径 file_path
     */
    private String filePath;

    /**
     * 文件地址 fastdfs_url
     */
    private String fastdfsUrl;

    /**
     * 文件后缀 file_suffix
     */
    private String fileSuffix;

    /**
     * 文件大小 file_size
     */
    private String size;

    /**
     * 创建时间 create_time
     */
    private LocalDateTime createTime;

    /**
     * 是否目录 is_directory
     */
    private boolean isDirectory;

    /**
     * 构造方法
     */
    public SysFileStorageDto() {
        super();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return the fileSuffix
     */
    public String getFileSuffix() {
        return fileSuffix;
    }

    /**
     * @param fileSuffix the fileSuffix to set
     */
    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    /**
     * @return the size
     */
    public String getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * @return the createTime
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the fastdfsUrl
     */
    public String getFastdfsUrl() {
        return fastdfsUrl;
    }

    /**
     * @param fastdfsUrl the fastdfsUrl to set
     */
    public void setFastdfsUrl(String fastdfsUrl) {
        this.fastdfsUrl = fastdfsUrl;
    }

    /**
     * @return the isDirectory
     */
    public boolean isDirectory() {
        return isDirectory;
    }

    /**
     * @param isDirectory the isDirectory to set
     */
    public void setDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the fileStorage
     */
    public StorageEnum getFileStorage() {
        return fileStorage;
    }

    /**
     * @param fileStorage the fileStorage to set
     */
    public void setFileStorage(StorageEnum fileStorage) {
        this.fileStorage = fileStorage;
    }
}
