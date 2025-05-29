package com.oner365.files.config;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.oner365.files.config.properties.FileFdfsProperties;
import com.oner365.files.storage.condition.FdfsStorageCondition;

/**
 * File Fdfs Config
 *
 * @author zhaoyong
 */
@Configuration
@Conditional(FdfsStorageCondition.class)
@EnableConfigurationProperties({ FileFdfsProperties.class })
public class FileFdfsConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileFdfsConfig.class);

    @Resource
    private FileFdfsProperties fileFdfsProperties;

    public FileFdfsConfig() {
        LOGGER.info("init file properties:{}", fileFdfsProperties);
    }

}
