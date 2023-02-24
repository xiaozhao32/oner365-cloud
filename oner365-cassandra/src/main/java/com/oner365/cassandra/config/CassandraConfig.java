package com.oner365.cassandra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;

/**
 * Cassandra config
 * 
 * @author zhaoyong
 *
 */
@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {
  
  @Value("${spring.data.cassandra.keyspace-name}")
  private String keyspaceName;

  @Override
  protected String getKeyspaceName() {
    return this.keyspaceName;
  }

}
