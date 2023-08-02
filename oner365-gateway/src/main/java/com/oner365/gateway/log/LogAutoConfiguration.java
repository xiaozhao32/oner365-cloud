package com.oner365.gateway.log;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import com.oner365.gateway.log.event.SysLogListener;
import com.oner365.gateway.service.SysLogService;

/**
 * 日志监听
 * @author zhaoyong
 */
@EnableAsync
@Configuration
@ConditionalOnWebApplication
public class LogAutoConfiguration {

    private final SysLogService sysLogService;

    public LogAutoConfiguration(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    @Bean
    SysLogListener sysLogListener() {
        return new SysLogListener(this.sysLogService);
    }

}
