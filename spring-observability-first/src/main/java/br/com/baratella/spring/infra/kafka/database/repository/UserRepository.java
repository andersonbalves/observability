package br.com.baratella.spring.infra.kafka.database.repository;

import br.com.baratella.spring.infra.kafka.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
