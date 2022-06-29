package br.com.baratella.spring.usecase.xpto;

import br.com.baratella.logger.entity.annotation.LogMethod;
import br.com.baratella.spring.usecase.xpto.repository.IXPTOUserAdapterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class XPTOUsecaseDefaultImpl implements IXPTOUsecase {

  private final IXPTOUserAdapterRepository repositoryAdapter;

  public XPTOUserDTOResponse execute(XPTOUserDTORequest user) throws InterruptedException {
    if ("sleep".equalsIgnoreCase(user.getName())) {
      Thread.sleep(2000);
    }
    if ("exception".equalsIgnoreCase(user.getName())) {
      throw new RuntimeException("Exceção lançada para teste");
    }
    return repositoryAdapter.save(user);
  }

}
