package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesubgroupsbymusclegroup.infrastructure;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.musclegroup.MuscleGroupsControllerDocumentation.MuscleGroupsControllerTag;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesubgroupsbymusclegroup.infrastructure.GetAllMuscleSubGroupsByMuscleGroupControllerDocumentation.Methods.GetAllMuscleSubGroupsByMuscleGroupDocumentation;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesubgroupsbymusclegroup.infrastructure.model.GetAllMuscleSubGroupByMuscleGroupResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@MuscleGroupsControllerTag
@RequestMapping(path = "${exercisesApiPrefix}/v1")
public interface GetAllMuscleSubGroupByMuscleGroupController {

  @GetAllMuscleSubGroupsByMuscleGroupDocumentation
  @GetMapping("muscleGroups/{muscleGroupId}/muscleSubGroups")
  @ResponseStatus(HttpStatus.OK)
  List<GetAllMuscleSubGroupByMuscleGroupResponse> execute(@PathVariable UUID muscleGroupId);
}
