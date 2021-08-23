package br.com.baratella.spring.adapter.repository;

import br.com.baratella.spring.infra.database.entity.UserEntity;
import br.com.baratella.spring.infra.database.repository.UserRepository;
import br.com.baratella.spring.usecase.xpto.XPTOUserDTORequest;
import br.com.baratella.spring.usecase.xpto.XPTOUserDTOResponse;
import br.com.baratella.spring.usecase.xpto.repository.IXPTOUserAdapterRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements IXPTOUserAdapterRepository {
  private final UserRepository userRepository;

  @Override
  public XPTOUserDTOResponse save(XPTOUserDTORequest request) {
    final UserEntity userEntity = userRepository.saveAndFlush(UserEntity.builder()
        .name(request.getName())
        .age(request.getAge())
        .build());
    return XPTOUserDTOResponse.builder()
        .name(userEntity.getName())
        .age(userEntity.getAge())
        .build();
  }

  @Override
  public List<XPTOUserDTOResponse> findAll() {
    List<UserEntity> userEntities = userRepository.findAll();
    return userEntities.stream()
        .map(u -> XPTOUserDTOResponse.builder()
            .name(u.getName())
            .age(u.getAge())
            .build())
    .collect(Collectors.toList());
  }

  @Override
  public XPTOUserDTOResponse findById(long id) {
    return userRepository.findById(id)
        .map(u -> XPTOUserDTOResponse.builder()
            .name(u.getName())
            .age(u.getAge())
            .build()
        )
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
  }

}
