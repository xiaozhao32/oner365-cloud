package com.oner365.gateway.constants;

import org.apache.logging.log4j.util.Strings;

/**
 * 服务错误信息
 * 
 * @author zhaoyong
 *
 */
public enum GatewayErrorEnum {

    /** 错误信息枚举 */
    ONER365_FILES("oner365-files", "文件中心"), 
    ONER365_SYSTEM("oner365-system", "权限中心"),
    ONER365_MONITOR("oner365-monitor", "监控服务"),
    ONER365_KAFKA("oner365-kafka", "Kafka服务"),
    ONER365_WEBSOCKET("oner365-websocket", "Websocket服务"),
    ONER365_ELASTICSEARCH("oner365-elasticsearch", "Elasticsearch服务");

    GatewayErrorEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    
    public static String getDescription(String name) {
        for (GatewayErrorEnum e : GatewayErrorEnum.values()) {
            if (name.contains(e.getName())) {
                return e.getDescription();
            }
        }
        return Strings.EMPTY;
    }

}
