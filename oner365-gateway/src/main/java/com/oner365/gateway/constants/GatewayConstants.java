package com.oner365.gateway.constants;

/**
 * 网关常量
 * @author zhaoyong
 */
public class GatewayConstants  {
    
    /**
     * 公共名称
     */
    public static final String NAME = "oner365";
    
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
    
    public static final String ROUT_STATUS_DISABLE = "0";
    
    public static final String CODE = "code";
    public static final String STATUS = "status";
    public static final String PATH = "path";
    public static final String METHOD = "method";
    public static final String MSG = "msg";
    public static final String MESSAGE = "message";
    public static final String RESULT = "result";
    
    /**
     * Constructor
     */
    private GatewayConstants() {
    }
}
