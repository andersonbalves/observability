package br.com.baratella.logger.entity;

import io.opentracing.Tracer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LoggerDTO {

  private String className;
  private String method;
  private Map<String, Object> params;
  private Tracer tracer;

  public LoggerDTO(JoinPoint joinPoint, Tracer tracer) {
    this.setClassName(joinPoint.getTarget().getClass().getName());
    this.setMethod(getCompleteMethodSignature(joinPoint));
    this.setParams(buildParamsMap(joinPoint));
    this.tracer = tracer;
  }

  private static String getCompleteMethodSignature(JoinPoint joinPoint) {
    String methodSignature =
        joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "(";
    List<String> argTypes = Arrays
        .stream(((MethodSignature) joinPoint.getSignature()).getParameterTypes())
        .map(param -> {
          String[] type = param.getCanonicalName().split("\\.");
          return type[type.length - 1];
        })
        .collect(Collectors.toList());
    String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
    if (argTypes.size() > 0) {
      for (int i = 0; i < argTypes.size(); i++) {
        methodSignature = methodSignature + argTypes.get(i) + " " + argNames[i];
        if (i < argTypes.size() - 1) {
          methodSignature = methodSignature + ", ";
        }
      }
    }
    methodSignature = methodSignature + ")";
    return methodSignature;
  }

  private static Map<String, Object> buildParamsMap(JoinPoint joinPoint) {
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
