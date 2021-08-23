package com.oner365.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger服务
 * 
 * @author zhaoyong
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableSwagger2
@ComponentScan({ "com.oner365.common", "com.oner365.swagger", "com.oner365.api" })
public class SpringSwaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSwaggerApplication.class, args);
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.oner365.swagger"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringBoot-Swagger2")
                .description("springboot | swagger")
                .contact(new Contact("oner365", "https://www.oner365.com", "service@oner365.com"))
                .version("1.0.0")
                .build();
    }
}
