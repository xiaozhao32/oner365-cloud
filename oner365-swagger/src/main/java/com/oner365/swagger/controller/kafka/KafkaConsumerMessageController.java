package com.oner365.swagger.controller.kafka;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.swagger.client.kafka.IKafkaConsumerClient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Kafka - 消息服务
 *
 * @author zhaoyong
 *
 */
@RestController
@Api(tags = "消息服务 - Kafka")
@RequestMapping("/kafka/message")
public class KafkaConsumerMessageController {

    @Resource
    private IKafkaConsumerClient client;

    /**
     * 发送消息
     * @param message 消息
     * @return ResponseData
     */
    @ApiOperation("1.消息")
    @ApiOperationSupport(order = 1)
    @GetMapping("/send")
    public ResponseData<String> send(String message) {
        return client.send(message);
    }

    /**
     * KafkaStreams 统计单词个数
     * @param word 单词内容
     * @return 统计单词个数
     */
    @ApiOperation("2.统计单词个数")
    @ApiOperationSupport(order = 2)
    @GetMapping("/word/count/{word}")
    public ResponseData<Long> getWordCount(@PathVariable String word) {
        return client.getWordCount(word);
    }

}
