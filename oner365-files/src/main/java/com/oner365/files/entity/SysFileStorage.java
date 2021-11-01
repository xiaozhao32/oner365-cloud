package com.oner365.files.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 文件对象
 * @author zhaoyong
 *
 */
@Entity
@Table(name = "nt_sys_fastdfs_file")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysFileStorage implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 主键 id
     */
    @Id
    private String id;
    
    /**
     * 文件名称 file_name
     */
    @Column(name = "file_name", nullable = false, length = 64)
    private String fileName;
    
    /**
     * 显示名称 display_name
     */
    @Column(name = "display_name", nullable = false, length = 64)
    private String displayName;
    
    /**
     * 存储方式
     */
    @Column(name = "file_storage", nullable = false)
    private String fileStorage;
    
    /**
     * 文件路径 file_path
     */
    @Column(name = "file_path", nullable = false, length = 255)
    private String filePath;
    
    /**
     * 文件地址 fastdfs_url
     */
    @Column(name = "fastdfs_url", nullable = false, length = 255)
    private String fastdfsUrl;
    
    /**
     * 文件后缀 file_suffix
     */
    @Column(name = "file_suffix", nullable = false, length = 8)
    private String fileSuffix;
    
    /**
     * 文件大小 file_size
     */
    @Column(name = "file_size", nullable = false, length = 64)
    private String size;
    
    /**
     * 创建时间 create_time
     */
    @Column(name = "create_time")
    private Date createTime;
    
    /**
     * 是否目录 is_directory
     */
    @Column(name = "is_directory")
    private boolean isDirectory;

    public SysFileStorage() {
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
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
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
    public String getFileStorage() {
        return fileStorage;
    }

    /**
     * @param fileStorage the fileStorage to set
     */
    public void setFileStorage(String fileStorage) {
        this.fileStorage = fileStorage;
    }
}
