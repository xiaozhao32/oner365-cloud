package com.oner365.dubbo.test.service;

import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.dubbo.api.service.IEchoService;
import com.oner365.dubbo.consumer.SpringDubboConsumerApplication;

/**
 * 单元测试 - dubbo
 *
 * @author zhaoyong
 */
@SpringBootTest(classes = SpringDubboConsumerApplication.class)
class EchoServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EchoServiceTest.class);

    @DubboReference
    private IEchoService service;

    @Test
    void test() {
        String message = "dubbo";
        String result = service.echo(message);
        LOGGER.info("test: {}", result);
        Assertions.assertNotNull(result);
    }

}
