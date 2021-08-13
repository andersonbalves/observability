package br.com.baratella.spring.infra.kafka.consumer;

import br.com.baratella.spring.kafka.avro.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@KafkaListener(
    topics = "users",
    groupId = "group_id_2",
    errorHandler = "customKafkaListenerErrorHandler")
public class Consumer {

  @Value("${topic.name}")
  private String topicName;

  @KafkaHandler(isDefault = true)
  public void consume(User record) {

  }
}