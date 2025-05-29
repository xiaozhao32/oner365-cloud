package com.oner365.sys.constants;

import com.oner365.data.commons.constants.PublicConstants;

/**
 * 消息常量
 *
 * @author zhaoyong
 */
public class SysMessageConstants extends PublicConstants {

    /**
     * 队列名称
     */
    public static final String QUEUE_NAME = PublicConstants.NAME + "." + "sysMessage";

    /**
     * 队列类型
     */
    public static final String QUEUE_TYPE = QUEUE_NAME + "." + "direct";

    /**
     * 队列标识
     */
    public static final String QUEUE_KEY = QUEUE_NAME + "." + "key";

    /**
     * Constructor
     */
    private SysMessageConstants() {
        super();
    }

}
