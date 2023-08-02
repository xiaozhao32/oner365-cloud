package com.oner365.vault.config;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.vault.annotation.VaultPropertySource;

import com.oner365.vault.config.properties.VaultProperties;

/**
 * Vault Config
 * 
 * @author zhaoyong
 */
@Configuration
@VaultPropertySource(value = "secret/oner365-springboot")
public class VaultConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(VaultConfig.class);

  @Resource
  private Environment env;

  @Bean
  VaultProperties getVaultProperties() {
    VaultProperties result = new VaultProperties();
    result.setName(env.getProperty("oner365.name"));
    result.setPassword(env.getProperty("oner365.password"));
    result.setUrl(env.getProperty("oner365.url"));
    LOGGER.info("vault properties: {}", result);
    return result;
  }

}
