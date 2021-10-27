package com.oner365.gateway.enums;

import org.apache.logging.log4j.util.Strings;

/**
 * 服务错误信息
 * 
 * @author zhaoyong
 *
 */
public enum GatewayErrorEnum {

    /** 错误信息枚举 */
    DUBBO("oner365-dubbo", "Dubbo服务"),
    ELASTICSEARCH("oner365-elasticsearch", "Elasticsearch服务"),
    FILES("oner365-files", "文件中心"), 
    GENERATOR("oner365-generator", "生成框架服务"),
    HADOOP("oner365-hadoop", "Hadoop服务"),
    KAFKA("oner365-kafka", "Kafka服务"),
    MONITOR("oner365-monitor", "监控服务"),
    ROCKETMQ("oner365-rocketmq", "Rocketmq服务"),
    SYSTEM("oner365-system", "权限中心"),
    WEBSOCKET("oner365-websocket", "Websocket服务")
    ;

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
