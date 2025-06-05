package com.oner365.ldap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Ldap服务
 *
 * @author zhaoyong
 */
@SpringBootApplication
@ComponentScan({ "com.oner365" })
public class SpringLdapApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringLdapApplication.class, args);
    }

}
