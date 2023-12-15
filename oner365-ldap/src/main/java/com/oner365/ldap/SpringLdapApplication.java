package com.oner365.ldap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Ldap服务
 * 
 * @author zhaoyong
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan({ "com.oner365.common", "com.oner365.ldap" })
public class SpringLdapApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringLdapApplication.class, args);
  }

}
