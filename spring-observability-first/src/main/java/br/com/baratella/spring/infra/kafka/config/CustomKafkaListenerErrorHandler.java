package br.com.baratella.spring.infra.kafka.config;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.message.ObjectMessage;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class CustomKafkaListenerErrorHandler implements KafkaListenerErrorHandler {

//  private final JaegerTracer tracer;

  @Override
  public Object handleError(Message<?> message, ListenerExecutionFailedException e) {
    Span.current().setStatus(StatusCode.ERROR, e.getMessage());
    log.error(new ObjectMessage(message).getFormattedMessage(), e);
    return null;
  }
}
