package com.oner365.gateway.constants;

import com.oner365.data.commons.constants.PublicConstants;

/**
 * 网关常量
 *
 * @author zhaoyong
 */
public class GatewayConstants extends PublicConstants {

    /**
     * 队列名称
     */
    public static final String QUEUE_NAME = NAME + "." + "syncRoute";

    /**
     * 队列标识
     */
    public static final String QUEUE_KEY = QUEUE_NAME + "." + "key";

    /**
     * 队列类型
     */
    public static final String QUEUE_TYPE = NAME + "." + "fanout";

    public static final String PREDICATE_NAME = "Path";

    public static final String PREDICATE_ARGS_PATTERN = "pattern";

    public static final int ROUT_STATUS_DISABLE = 0;

    /**
     * Constructor
     */
    private GatewayConstants() {
    }

}
