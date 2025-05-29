package com.oner365.statemachine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Statemachine 服务
 *
 * @author zhaoyong
 */
@SpringBootApplication
@ComponentScan({ "com.oner365" })
public class SpringStatemachineApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringStatemachineApplication.class, args);
    }

}
