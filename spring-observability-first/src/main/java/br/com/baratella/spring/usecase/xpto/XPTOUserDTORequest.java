package br.com.baratella.spring.usecase.xpto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonFormat
public class XPTOUserDTORequest {
  private String name;
  private int age;
}
