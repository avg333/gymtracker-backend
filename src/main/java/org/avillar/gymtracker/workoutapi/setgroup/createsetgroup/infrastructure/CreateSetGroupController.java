package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure;

import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.setgroup.SetGroupControllerDocumentation.SetGroupControllerTag;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.CreateSetGroupControllerDocumentation.Methods.CreateSetGroupDocumentation;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupRequest;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@SetGroupControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface CreateSetGroupController {

  @CreateSetGroupDocumentation
  @PostMapping("/workouts/{workoutId}/setGroups")
  @ResponseStatus(HttpStatus.OK)
  CreateSetGroupResponse execute(
      @PathVariable UUID workoutId, @Valid @RequestBody CreateSetGroupRequest createSetGroupRequest)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException, ExerciseUnavailableException;
}
