package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure.model.GetAllMuscleGroupsByMuscleSupGroupResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${exercisesApiPrefix}")
public interface GetAllMuscleGroupsByMuscleSupGroupController {

  /**
   * @deprecated Only for develop
   */
  @Deprecated(forRemoval = true)
  @GetMapping("muscleSupGroups/{muscleSupGroupId}/muscleGroups")
  ResponseEntity<List<GetAllMuscleGroupsByMuscleSupGroupResponseInfrastructure>> execute(
      @PathVariable UUID muscleSupGroupId);
}
