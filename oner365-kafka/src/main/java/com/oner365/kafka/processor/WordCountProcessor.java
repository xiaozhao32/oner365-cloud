package com.oner365.kafka.processor;

import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.springframework.stereotype.Component;

import com.oner365.kafka.constants.KafkaConstants;

/**
 * KafkaStreams 流式计算统计单词
 * 
 * @author zhaoyong
 */
@Component
public class WordCountProcessor {

  private static final Serde<String> STRING_SERDE = Serdes.String();

  @Resource
  public void buildPipeline(StreamsBuilder streamsBuilder) {
    KStream<String, String> messageStream = streamsBuilder.stream(KafkaConstants.TOPIC_2,
        Consumed.with(STRING_SERDE, STRING_SERDE));

    KTable<String, Long> wordCounts = messageStream.mapValues((ValueMapper<String, String>) String::toLowerCase)
        .flatMapValues(value -> Arrays.asList(value.split("\\W+")))
        .groupBy((key, word) -> word, Grouped.with(STRING_SERDE, STRING_SERDE)).count(Materialized.as("counts"));

    wordCounts.toStream().to(KafkaConstants.TOPIC_3);
  }
}
