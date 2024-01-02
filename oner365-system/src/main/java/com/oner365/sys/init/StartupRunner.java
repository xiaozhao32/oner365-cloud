package com.oner365.sys.init;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.enums.ErrorInfoEnum;
import com.oner365.data.commons.enums.ExistsEnum;
import com.oner365.data.commons.enums.ResultEnum;
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.enums.StorageEnum;
import com.oner365.sys.constants.SysMessageConstants;
import com.oner365.sys.enums.MessageStatusEnum;
import com.oner365.sys.enums.MessageTypeEnum;
import com.oner365.sys.enums.SysUserSexEnum;
import com.oner365.sys.enums.SysUserTypeEnum;

/**
 * 初始化应用配置
 * 
 * @author zhaoyong
 *
 */
@Component
public class StartupRunner implements ApplicationRunner {
  
  private final Logger logger = LoggerFactory.getLogger(StartupRunner.class);
  
  @Resource
  private RabbitAdmin rabbitAdmin; 

  @Override
  public void run(ApplicationArguments args) {
    initEnum();
  }
  
  /**
   * 初始化枚举
   */
  private void initEnum() {
    /* common */
    PublicConstants.initEnumMap.put(PublicConstants.PARAM_STATUS, StatusEnum.class.getName());
    PublicConstants.initEnumMap.put(PublicConstants.PARAM_FILE_STORAGE, StorageEnum.class.getName());
    PublicConstants.initEnumMap.put("errorInfo", ErrorInfoEnum.class.getName());
    PublicConstants.initEnumMap.put("exists", ExistsEnum.class.getName());
    PublicConstants.initEnumMap.put("result", ResultEnum.class.getName());
    
    /* system */
    PublicConstants.initEnumMap.put("messageStatus", MessageStatusEnum.class.getName());
    PublicConstants.initEnumMap.put("messageType", MessageTypeEnum.class.getName());
    PublicConstants.initEnumMap.put("sex", SysUserSexEnum.class.getName());
    PublicConstants.initEnumMap.put("sysUserType", SysUserTypeEnum.class.getName());
    
    logger.info("Initializing Oner365 Enum map.");
  }
  
  @PreDestroy
  public void destroy() {
    PublicConstants.initEnumMap.clear();
    logger.info("Destroy Oner365 config.");
    
    rabbitAdmin.deleteQueue(SysMessageConstants.QUEUE_NAME);
    logger.info("Destroy Rabbitmq queue.");

    rabbitAdmin.deleteExchange(SysMessageConstants.QUEUE_TYPE);
    logger.info("Destroy Rabbitmq exchange.");
  }

}
