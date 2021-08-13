package br.com.baratella.logger.usecase;

import br.com.baratella.logger.entity.LoggerDTO;
import io.opentracing.Tracer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.ObjectMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class AOPAnnotationLogger {

  private final Tracer tracer;

  @Pointcut("execution(@br.com.baratella.logger.annotation.LogMethod * *(..))")
  private void annotatedMethodPointcut() {
  }

  @Around("annotatedMethodPointcut()")
  public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
    LoggerDTO dto = new LoggerDTO(joinPoint, tracer);

    log.info("-> Método " + dto.getMethod() + " iniciado com as seguintes informações:\n"
        + new ObjectMessage(dto).getFormattedMessage());

    long startTime = new Date().getTime();
    Object result = joinPoint.proceed(joinPoint.getArgs());
    long endTime = new Date().getTime();

    log.info("<- O método " + dto.getMethod() + " levou " +
        (endTime - startTime) + "ms e retornou:\n" + new ObjectMessage(result)
        .getFormattedMessage());

    return result;
  }

  private Map<String, Object> buildParamsMap(ProceedingJoinPoint joinPoint) {
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
