package com.oner365.files.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.oner365.files.config.properties.FileLocalProperties;
import com.oner365.files.storage.condition.LocalStorageCondition;

/**
 * File Local Config
 * 
 * @author zhaoyong
 */
@Configuration
@Conditional(LocalStorageCondition.class)
@EnableConfigurationProperties({ FileLocalProperties.class })
public class FileLocalConfig {

}
