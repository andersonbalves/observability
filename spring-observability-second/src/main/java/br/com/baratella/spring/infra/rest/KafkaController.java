package br.com.baratella.spring.infra.rest;

import br.com.baratella.spring.infra.kafka.producer.Producer;
import br.com.baratella.spring.kafka.avro.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@Log4j2
@RequiredArgsConstructor
public class KafkaController {

  private final Producer producer;

  @PostMapping(value = "/publish")
  public void sendMessageToKafkaTopic(@RequestParam("name") String name,
      @RequestParam("age") Integer age) {
    this.producer.sendMessage(new User(name, age));
  }
}