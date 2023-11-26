package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.SetGroupControllerDocumentation.SetGroupControllerTag;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.GetSetGroupsByExerciseControllerDocumentation.Methods.GetSetGroupsByExerciseDocumentation;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.model.GetSetGroupsByExerciseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@SetGroupControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface GetSetGroupsByExerciseController {

  @GetSetGroupsByExerciseDocumentation
  @GetMapping("/users/{userId}/exercises/{exerciseId}/setGroups")
  @ResponseStatus(HttpStatus.OK)
  List<GetSetGroupsByExerciseResponse> execute(
      @PathVariable UUID userId, @PathVariable UUID exerciseId)
      throws WorkoutIllegalAccessException;
}
