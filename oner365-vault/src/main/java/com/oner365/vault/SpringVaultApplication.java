package com.oner365.vault;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Vault 服务
 * 
 * @author zhaoyong
 */
@SpringBootApplication
@ComponentScan({ "com.oner365" })
public class SpringVaultApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringVaultApplication.class, args);
  }

}
