package br.com.baratella.logger.usecase;

import br.com.baratella.logger.entity.LoggerDTO;
import io.opentracing.Tracer;
import java.util.Arrays;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.ObjectMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class AOPControllerLogger {

  private final Tracer tracer;

  @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
  private void restControllerPointcut() {
  }

  @Around("restControllerPointcut()")
  public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
    LoggerDTO dto = new LoggerDTO(joinPoint, tracer);
    tracer.activeSpan().setTag("class-name", dto.getClassName());
    tracer.activeSpan().setTag("method", dto.getMethod());
    Arrays.stream(joinPoint.getArgs()).
        forEach(e -> tracer.activeSpan().setTag(e.getClass().getSimpleName(), new ObjectMessage(e).getFormat()));

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

}
