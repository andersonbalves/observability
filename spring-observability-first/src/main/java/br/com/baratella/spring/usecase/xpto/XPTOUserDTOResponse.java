package br.com.baratella.spring.usecase.xpto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class XPTOUserDTOResponse {
  private String name;
  private int age;
}
