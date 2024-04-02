package com.oner365.zookeeper.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.zookeeper.config.properties.ZooKeeperProperties;

/**
 * Zookeeper Config
 * 
 * @author zhaoyong
 */
@Configuration
@EnableConfigurationProperties({ ZooKeeperProperties.class })
public class ZookeeperConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperConfig.class);

  private final ZooKeeperProperties properties;

  public ZookeeperConfig(ZooKeeperProperties properties) {
    this.properties = properties;
  }

  @Bean
  CuratorFramework curatorFramework() {
    LOGGER.info("init Zookeeper properties:{}", properties.getConnectString());
    
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(properties.getBaseSleepTimeMs(), properties.getMaxRetries());

    CuratorFramework client = CuratorFrameworkFactory.builder().connectString(properties.getConnectString())
        .namespace(PublicConstants.NAME)
        .connectionTimeoutMs((int) properties.getConnectionTimeout().toMillis())
        .sessionTimeoutMs((int) properties.getSessionTimeout().toMillis()).retryPolicy(retryPolicy).build();
    client.start();
    return client;
  }
}
