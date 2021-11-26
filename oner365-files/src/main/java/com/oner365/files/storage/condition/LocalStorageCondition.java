package com.oner365.files.storage.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.StorageEnum;

/**
 * 本地上传模式
 * 
 * @author zhaoyong
 *
 */
public class LocalStorageCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata metadata) {
        Environment environment = conditionContext.getEnvironment();
        String type = environment.getProperty(PublicConstants.FILE_STORAGE);
        // 本地上传
        return type == null || type.equals(StorageEnum.LOCAL.getCode());
    }

}
