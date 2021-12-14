package com.oner365.gateway.log.event;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import com.oner365.gateway.service.SysLogService;

/**
 * 系统日志监听
 * @author zhaoyong
 */
public class SysLogListener {

    private final SysLogService sysLogService;
    
    public SysLogListener(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    @Async
    @Order
    @EventListener({ SysLogEvent.class })
    public void saveSysLog(SysLogEvent event) {
        this.sysLogService.save(event.getSysLogVo());
    }
}
