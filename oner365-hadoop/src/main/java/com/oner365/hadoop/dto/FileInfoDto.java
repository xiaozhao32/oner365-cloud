package com.oner365.hadoop.dto;

import java.io.Serializable;

/**
 * Hadoop 文件信息
 * 
 * @author zhaoyong
 *
 */
public class FileInfoDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 文件路径
   */
  private String filePath;
  
  /**
   * 文件名称
   */
  private String fileName;
  
  /**
   * 文件长度
   */
  private Long size;
  
  /**
   * 是否目录
   */
  private Boolean isDirectory;

  public FileInfoDto() {
    super();
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  public Boolean getIsDirectory() {
    return isDirectory;
  }

  public void setIsDirectory(Boolean isDirectory) {
    this.isDirectory = isDirectory;
  }

}
