package br.com.baratella.spring.infra.rest.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDTO {
  private String name;
  private int age;
}
