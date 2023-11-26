package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesupgroups.infrastructure;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclegroup.MuscleGroupsControllerDocumentation.MuscleGroupsControllerTag;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesupgroups.infrastructure.GetAllMuscleSupGroupsControllerDocumentation.Methods.GetAllMuscleSupGroupsDocumentation;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesupgroups.infrastructure.model.GetAllMuscleSupGroupsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@MuscleGroupsControllerTag
@RequestMapping(path = "${exercisesApiPrefix}/v1")
public interface GetAllMuscleSupGroupsController {

  @GetAllMuscleSupGroupsDocumentation
  @GetMapping("muscleSupGroups")
  @ResponseStatus(HttpStatus.OK)
  List<GetAllMuscleSupGroupsResponse> execute();
}
