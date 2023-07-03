package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.model.GetAllLoadTypesResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${exercisesApiPrefix}")
public interface GetAllLoadTypesController {

  @GetMapping("/loadTypes")
  ResponseEntity<List<GetAllLoadTypesResponseInfrastructure>> execute();
}
