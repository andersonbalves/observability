package br.com.baratella.spring.infra.kafka.consumer;

import br.com.baratella.spring.kafka.avro.User;
import br.com.baratella.spring.usecase.xpto.IXPTOUsecase;
import br.com.baratella.spring.usecase.xpto.XPTOUserDTORequest;
import br.com.baratella.spring.usecase.xpto.XPTOUserDTOResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
@KafkaListener(
    topics = "users",
    groupId = "group_id_1",
    errorHandler = "customKafkaListenerErrorHandler")
public class Consumer {

  private final IXPTOUsecase xptoUsecase;

  @KafkaHandler(isDefault = true)
  public void consume(User record) throws InterruptedException {
    log.info(String.format("Consumed message -> %s", record));
    XPTOUserDTOResponse response = xptoUsecase.execute(XPTOUserDTORequest.builder()
        .name(record.getName())
        .age(record.getAge())
        .build());
    log.info("Usuário salvo com sucesso -> %s", response);
  }
}