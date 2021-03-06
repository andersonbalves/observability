package br.com.baratella.spring.infra.kafka.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class CustomKafkaListenerErrorHandler implements KafkaListenerErrorHandler {

  @Override
  public Object handleError(Message<?> message, ListenerExecutionFailedException e) {
    log.error(message, e);
    return null;
  }
}
