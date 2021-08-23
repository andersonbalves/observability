package br.com.baratella.spring.infra.kafka.config;

import io.jaegertracing.internal.JaegerSpan;
import io.jaegertracing.internal.JaegerTracer;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.log.Fields;
import io.opentracing.tag.Tag;
import io.opentracing.tag.Tags;
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

  private final JaegerTracer tracer;

  @Override
  public Object handleError(Message<?> message, ListenerExecutionFailedException e) {
    Map<String, Object> messageMap = new HashMap<>();
    messageMap.put("message", message);
    messageMap.put("tracer", tracer);
    messageMap.put(Fields.ERROR_OBJECT, e);
    log.error(new ObjectMessage(messageMap).getFormattedMessage(), e);
    return null;
  }
}
