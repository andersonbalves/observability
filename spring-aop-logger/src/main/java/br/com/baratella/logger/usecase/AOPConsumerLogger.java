package br.com.baratella.logger.usecase;

import br.com.baratella.logger.entity.LoggerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.JsonObject;
import io.opentracing.Tracer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.ObjectMessage;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class AOPConsumerLogger {

  private final Tracer tracer;

  @Pointcut("@within(org.springframework.kafka.annotation.KafkaListener)")
  private void consumerClassPointcut() {
  }

  @Pointcut("execution(@org.springframework.kafka.annotation.KafkaListener * *(..))")
  private void consumerMethodPointcut() {
  }


  @Before("consumerClassPointcut() || consumerMethodPointcut()")
  public void logBefore(JoinPoint joinPoint) throws Throwable {
    LoggerDTO dto = new LoggerDTO(joinPoint, tracer);
    tracer.activeSpan().setTag("class-name", dto.getClassName());
    tracer.activeSpan().setTag("method", dto.getMethod());
    Arrays.stream(joinPoint.getArgs()).
        forEach(e -> tracer.activeSpan().setTag(e.getClass().getSimpleName(), new ObjectMessage(e).getFormat()));


    log.info("-> Método " + dto.getMethod() + " iniciado com as seguintes informações:\n"
        + new ObjectMessage(dto).getFormattedMessage());
  }

  private Map<String, Object> buildParamsMap(JoinPoint joinPoint) {
    String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
    Object[] values = joinPoint.getArgs();
    Map<String, Object> params = new HashMap<>();
    if (argNames.length != 0) {
      for (int i = 0; i < argNames.length; i++) {
        params.put(argNames[i], values[i]);
      }
    }
    return params;
  }

}
