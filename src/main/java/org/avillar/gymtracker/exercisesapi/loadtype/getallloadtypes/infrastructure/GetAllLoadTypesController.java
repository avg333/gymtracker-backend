package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.loadtype.LoadTypesControllerDocumentation.LoadTypesControllerTag;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.GetAllLoadTypesControllerDocumentation.Methods.GetAllLoadTypesDocumentation;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.model.GetAllLoadTypesResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@LoadTypesControllerTag
@RequestMapping(path = "${exercisesApiPrefix}/v1")
public interface GetAllLoadTypesController {

  @GetAllLoadTypesDocumentation
  @GetMapping("/loadTypes")
  @ResponseStatus(HttpStatus.OK)
  List<GetAllLoadTypesResponse> execute();
}
