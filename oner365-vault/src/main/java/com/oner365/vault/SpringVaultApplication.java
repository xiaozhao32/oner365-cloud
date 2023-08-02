package com.oner365.vault;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Vault 服务
 * 
 * @author zhaoyong
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan({ "com.oner365.vault" })
public class SpringVaultApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringVaultApplication.class, args);
  }

}
