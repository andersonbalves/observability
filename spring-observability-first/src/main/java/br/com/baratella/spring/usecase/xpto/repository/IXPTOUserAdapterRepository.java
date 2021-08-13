package br.com.baratella.spring.usecase.xpto.repository;

import br.com.baratella.spring.usecase.xpto.XPTOUserDTORequest;
import br.com.baratella.spring.usecase.xpto.XPTOUserDTOResponse;
import java.util.List;

public interface IXPTOUserAdapterRepository {
  XPTOUserDTOResponse save(XPTOUserDTORequest request);
  List<XPTOUserDTOResponse> findAll();
  XPTOUserDTOResponse findById(long id);
}
