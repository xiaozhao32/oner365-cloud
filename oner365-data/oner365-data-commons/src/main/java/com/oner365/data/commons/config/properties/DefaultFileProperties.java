package com.oner365.data.commons.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 文件内容处理配置
 *
 * @author zhaoyong
 */
@Configuration
@ConfigurationProperties(prefix = "file")
public class DefaultFileProperties {

    /**
     * 文件存储类型: local fdfs minio
     */
    private String storage;

    /**
     * 文件下载地址
     */
    private String download;

    /**
     * excel导入导出后缀
     */
    @Value("${file.excel.suffix:xlsx}")
    private String excelSuffix;

    /**
     * 构造方法
     */
    public DefaultFileProperties() {
        super();
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getExcelSuffix() {
        return excelSuffix;
    }

    public void setExcelSuffix(String excelSuffix) {
        this.excelSuffix = excelSuffix;
    }

}
