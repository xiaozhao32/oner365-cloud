package com.oner365.files.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FileLocalConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileLocalConfig.class);

  @Autowired
  private FileLocalProperties fileLocalProperties;

  public FileLocalConfig() {
    LOGGER.info("init file properties:{}", fileLocalProperties);
  }
}
