package br.com.baratella.spring.infra.kafka.producer;

import br.com.baratella.spring.kafka.avro.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Log4j2
@RequiredArgsConstructor
public class Producer {

  private final KafkaTemplate<String, User> kafkaTemplate;

  @Value("${topic.name}")
  private String TOPIC;

  public void sendMessage(User user) {
    ListenableFuture<SendResult<String, User>> future = this.kafkaTemplate.send(this.TOPIC, user);
    future.addCallback(new ListenableFutureCallback<>() {
      @Override
      public void onSuccess(SendResult<String, User> result) {
        log.info(String.format("Produced user -> %s", result));
      }

      @Override
      public void onFailure(Throwable ex) {
        log.error(ex);
      }
    });
  }

}
