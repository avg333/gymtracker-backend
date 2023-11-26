package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.musclegroup.MuscleGroupsControllerDocumentation.MuscleGroupsControllerTag;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure.GetAllMuscleGroupsControllerDocumentation.Methods.GetAllMuscleGroupsDocumentation;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure.model.GetAllMuscleGroupsByMuscleSupGroupResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@MuscleGroupsControllerTag
@RequestMapping(path = "${exercisesApiPrefix}/v1")
public interface GetAllMuscleGroupsByMuscleSupGroupController {

  @GetAllMuscleGroupsDocumentation
  @GetMapping("muscleSupGroups/{muscleSupGroupId}/muscleGroups")
  @ResponseStatus(HttpStatus.OK)
  List<GetAllMuscleGroupsByMuscleSupGroupResponse> execute(@PathVariable UUID muscleSupGroupId);
}
