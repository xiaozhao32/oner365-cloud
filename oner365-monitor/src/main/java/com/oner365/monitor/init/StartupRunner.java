package com.oner365.monitor.init;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.oner365.api.enums.MisfirePolicyEnum;
import com.oner365.api.enums.TaskStatusEnum;
import com.oner365.common.constants.PublicConstants;
import com.oner365.monitor.enums.RabbitmqTypeEnum;

/**
 * 初始化应用配置
 * 
 * @author zhaoyong
 *
 */
@Component
public class StartupRunner implements ApplicationRunner {
  
  private final Logger logger = LoggerFactory.getLogger(StartupRunner.class);

  @Override
  public void run(ApplicationArguments args) {
    initEnum();
  }
  
  /**
   * 初始化枚举
   */
  private void initEnum() {
    /* monitor */
    PublicConstants.initEnumMap.put("misfirePolicy", MisfirePolicyEnum.class.getName());
    PublicConstants.initEnumMap.put("rabbitmqType", RabbitmqTypeEnum.class.getName());
    PublicConstants.initEnumMap.put("taskStatus", TaskStatusEnum.class.getName());
    
    logger.info("Initializing Oner365 Enum map.");
  }
  
  @PreDestroy
  public void destroy() {
    PublicConstants.initEnumMap.clear();
    logger.info("Destroy Oner365 config.");
  }

}
