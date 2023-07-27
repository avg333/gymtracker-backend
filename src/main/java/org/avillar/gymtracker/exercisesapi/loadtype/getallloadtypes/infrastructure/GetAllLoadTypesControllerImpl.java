package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.GetLoadTypeService;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.mapper.GetAllLoadTypesControllerMapper;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.model.GetAllLoadTypesResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetAllLoadTypesControllerImpl implements GetAllLoadTypesController {

  private final GetLoadTypeService getLoadTypeService;
  private final GetAllLoadTypesControllerMapper getAllLoadTypesControllerMapper;

  @Override
  public List<GetAllLoadTypesResponse> execute() {
    return getAllLoadTypesControllerMapper.map(getLoadTypeService.execute());
  }
}
