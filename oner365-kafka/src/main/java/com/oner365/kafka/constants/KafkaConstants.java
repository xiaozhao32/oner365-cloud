package com.oner365.kafka.constants;

/**
 * kafka 常量设置
 * 
 * @author zhaoyong
 */
public class KafkaConstants {

  public static final String APPLICATION_ID_CONFIG = "application.id";
  public static final String BOOTSTRAP_SERVERS_CONFIG = "bootstrap.servers";
  public static final String DEFAULT_KEY_SERDE_CLASS_CONFIG = "default.key.serde.class.config";
  public static final String DEFAULT_VALUE_SERDE_CLASS_CONFIG = "default.value.serde.class.config";
  
  /** spring.kafka.topics */
  public static final String TOPIC_1 = "test";
  public static final String TOPIC_2 = "test2";
  public static final String TOPIC_3 = "test3";

  private KafkaConstants() {
  }

}
