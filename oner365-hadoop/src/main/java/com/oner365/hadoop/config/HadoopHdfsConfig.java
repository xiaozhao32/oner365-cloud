package com.oner365.hadoop.config;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.Resource;

import org.apache.hadoop.fs.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oner365.hadoop.config.properties.HdfsProperties;

/**
 * Hadoop Config
 *
 * @author zhaoyong
 *
 */
@Configuration
@EnableConfigurationProperties({ HdfsProperties.class })
public class HadoopHdfsConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(HadoopHdfsConfig.class);

    @Resource
    private HdfsProperties hdfsProperties;

    /**
     * 获取HDFS文件系统对象
     * @return FileSystem
     */
    @Bean
    FileSystem getFileSystem() {
        // 客户端去操作hdfs时是有一个用户身份的，默认情况下hdfs客户端api会从jvm中获取一个参数作为自己的用户身份
        // HADOOP_USER_NAME=hadoop
        // 也可以在构造客户端fs对象时，通过参数传递进去
        try {
            org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
            configuration.set("fs.defaultFS", hdfsProperties.getPath());
            return FileSystem.get(new URI(hdfsProperties.getPath()), configuration, hdfsProperties.getUsername());
        }
        catch (InterruptedException e) {
            LOGGER.error("FileSystem InterruptedException:", e);
            Thread.currentThread().interrupt();
        }
        catch (IOException e) {
            LOGGER.error("FileSystem IOException:", e);
        }
        catch (URISyntaxException e) {
            LOGGER.error("FileSystem URISyntaxException:", e);
        }
        return null;
    }

}
