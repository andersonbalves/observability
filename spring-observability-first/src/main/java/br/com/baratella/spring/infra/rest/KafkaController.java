package br.com.baratella.spring.infra.rest;

import br.com.baratella.spring.infra.kafka.producer.Producer;
import br.com.baratella.spring.infra.rest.model.UserRequestDTO;
import br.com.baratella.spring.kafka.avro.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class KafkaController {

  private final Producer producer;

  @CrossOrigin
  @PostMapping
  public void postUser(@RequestBody UserRequestDTO user) {
    if (StringUtils.isEmpty(user.getName())) {
      throw new IllegalArgumentException("Parâmetro \"name\" não pode ser vazio");
    }
    this.producer.sendMessage(User.newBuilder()
        .setName(user.getName())
        .setAge(user.getAge())
        .build());
  }
}