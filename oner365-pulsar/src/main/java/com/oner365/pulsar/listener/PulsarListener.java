package com.oner365.pulsar.listener;

import javax.annotation.Resource;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.oner365.pulsar.config.properties.PulsarProperties;

/**
 * pulsar listener
 *
 * @author zhaoyong
 *
 */
@Component
public class PulsarListener implements MessageListener<JSONObject> {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(PulsarListener.class);

    @Resource
    private PulsarProperties pulsarProperties;

    @Override
    public void received(Consumer<JSONObject> consumer, Message<JSONObject> msg) {
        try {
            byte[] bytes = msg.getData();
            if (bytes != null) {
                String data = new String(bytes);
                LOGGER.info("Pulsar data: {}, topic: {}", data, consumer.getTopic());
                consumer.acknowledge(msg);
            }
        }
        catch (PulsarClientException e) {
            consumer.negativeAcknowledge(msg);
        }
    }

}
