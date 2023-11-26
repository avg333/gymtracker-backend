package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.common.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.common.facade.loadtype.LoadTypeFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetLoadTypeServiceImpl implements GetLoadTypeService {

  private final LoadTypeFacade loadTypeFacade;

  @Override
  public List<LoadType> execute() {
    return loadTypeFacade.getAllLoadTypes();
  }
}
