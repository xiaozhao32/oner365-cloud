package com.oner365.mqtt.constants;

import com.oner365.data.commons.constants.PublicConstants;

/**
 * mqtt 常量设置
 *
 * @author zhaoyong
 */
public class MqttConstants extends PublicConstants {

    public static final String IN_BOUND_CHANNEL = "mqttInboundChannel";

    public static final String OUT_BOUND_CHANNEL = "mqttOutboundChannel";

    public static final String CHANNEL_ADAPTER = "_adapter";

    public static final String CHANNEL_PRODUCER = "_producer";

    public static final String SUBCRIPTION = "mqtt-subscription-";

    public static final Integer COMPLETION_TIMEOUT = 5000;

    public static final String QOS_NAME = "qos";

    public static final Integer QOS = 1;

    private MqttConstants() {
    }

}
