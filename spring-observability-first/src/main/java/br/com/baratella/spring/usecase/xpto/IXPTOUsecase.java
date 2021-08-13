package br.com.baratella.spring.usecase.xpto;

import org.springframework.stereotype.Service;

@Service
public interface IXPTOUsecase {

  XPTOUserDTOResponse execute(XPTOUserDTORequest user) throws InterruptedException;

}
