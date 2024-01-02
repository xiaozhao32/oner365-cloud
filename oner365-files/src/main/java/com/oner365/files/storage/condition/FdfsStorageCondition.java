package com.oner365.files.storage.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;

import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.enums.StorageEnum;

/**
 * fdfs上传模式
 * 
 * @author zhaoyong
 *
 */
public class FdfsStorageCondition implements Condition {

  @Override
  public boolean matches(ConditionContext conditionContext, @NonNull AnnotatedTypeMetadata metadata) {
    Environment environment = conditionContext.getEnvironment();
    String type = environment.getProperty(PublicConstants.FILE_STORAGE);
    // fdfs上传
    return type != null && type.equalsIgnoreCase(StorageEnum.FDFS.name());
  }

}
