package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.GetLoadTypeService;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.mapper.GetAllLoadTypesControllerMapper;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.model.GetAllLoadTypesResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetAllLoadTypesControllerImpl implements GetAllLoadTypesController {

  private final GetLoadTypeService getLoadTypeService;
  private final GetAllLoadTypesControllerMapper getAllLoadTypesControllerMapper;

  @Override
  public ResponseEntity<List<GetAllLoadTypesResponseInfrastructure>> execute() {
    return ResponseEntity.ok(getAllLoadTypesControllerMapper.map(getLoadTypeService.execute()));
  }
}
