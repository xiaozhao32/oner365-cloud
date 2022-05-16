package com.oner365.files.storage.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.StorageEnum;
import org.springframework.lang.NonNull;

/**
 * minio上传模式
 * 
 * @author zhaoyong
 *
 */
public class MinioStorageCondition implements Condition {

  @Override
  public boolean matches(ConditionContext conditionContext, @NonNull AnnotatedTypeMetadata metadata) {
    Environment environment = conditionContext.getEnvironment();
    String type = environment.getProperty(PublicConstants.FILE_STORAGE);
    // minio上传
    return type != null && type.equalsIgnoreCase(StorageEnum.MINIO.name());
  }

}
