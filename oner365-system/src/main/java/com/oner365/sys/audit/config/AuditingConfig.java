package com.oner365.sys.audit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 开启审计功能
 * 
 * @author zhaoyong
 */
@Configuration
@EnableJpaAuditing
public class AuditingConfig {

}
