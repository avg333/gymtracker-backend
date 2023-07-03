package org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.infrastructure;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.infrastructure.model.GetAllMuscleSubGroupByMuscleGroupResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${exercisesApiPrefix}")
public interface GetAllMuscleSubGroupByMuscleGroupController {

  /**
   * @deprecated Only for develop
   */
  @Deprecated(forRemoval = true)
  @GetMapping("muscleGroups/{muscleGroupId}/muscleSubGroups")
  ResponseEntity<List<GetAllMuscleSubGroupByMuscleGroupResponseInfrastructure>> execute(
      @PathVariable UUID muscleGroupId);
}
