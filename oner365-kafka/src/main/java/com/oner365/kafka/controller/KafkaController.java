package com.oner365.kafka.controller;

import javax.annotation.Resource;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.commons.enums.ResultEnum;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.web.controller.BaseController;
import com.oner365.kafka.processor.WordCountProcessor;
import com.oner365.kafka.service.IKafkaConsumerService;

/**
 * Kafka控制器
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/message")
public class KafkaController extends BaseController {

  @Resource
  private IKafkaConsumerService service;

  @Resource
  private StreamsBuilderFactoryBean factoryBean;

  @Resource
  private WordCountProcessor wordCountProcessor;

  /**
   * 发送消息
   * 
   * @param message 消息
   * @return String
   */
  @GetMapping("/send")
  public ResponseData<String> send(String message) {
    logger.info("Kafka send message:{}", message);
    ResultEnum result = service.convertAndSend(message);
    if (ResultEnum.SUCCESS.equals(result)) {
      return ResponseData.success(message);
    }
    return ResponseData.error(result.getName());
  }

  /**
   * KafkaStreams 统计单词个数
   * 
   * @param word 单词内容
   * @return 统计单词个数
   */
  @GetMapping("/word/count/{word}")
  public ResponseData<Long> getWordCount(@PathVariable String word) {
    KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
    if (kafkaStreams != null) {
      ReadOnlyKeyValueStore<String, Long> counts = kafkaStreams
          .store(StoreQueryParameters.fromNameAndType("counts", QueryableStoreTypes.keyValueStore()));
      Long result = counts.get(word);
      return ResponseData.success(result);
    }
    return ResponseData.error(ResultEnum.ERROR.getName());
  }
}
