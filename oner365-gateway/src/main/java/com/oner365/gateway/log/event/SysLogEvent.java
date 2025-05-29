package com.oner365.gateway.log.event;

import com.oner365.gateway.vo.SysLogVo;

/**
 * 系统日志监听
 *
 * @author zhaoyong
 */
public class SysLogEvent {

    private SysLogVo sysLogVo;

    public SysLogEvent(SysLogVo sysLogVo) {
        super();
        this.sysLogVo = sysLogVo;
    }

    /**
     * Get SysLogVo
     * @return the sysLogVo
     */
    public SysLogVo getSysLogVo() {
        return sysLogVo;
    }

}
