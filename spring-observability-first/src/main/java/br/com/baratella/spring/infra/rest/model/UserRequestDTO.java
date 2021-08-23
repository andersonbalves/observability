package br.com.baratella.spring.infra.rest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonFormat
public class UserRequestDTO {
  private String name;
  private int age;
}
