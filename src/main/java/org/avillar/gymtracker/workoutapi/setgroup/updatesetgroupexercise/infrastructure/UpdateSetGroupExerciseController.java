package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure;

import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.SetGroupControllerDocumentation.SetGroupControllerTag;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.UpdateSetGroupExerciseControllerDocumentation.Methods.UpdateSetGroupExerciseDocumentation;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model.UpdateSetGroupExerciseRequest;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model.UpdateSetGroupExerciseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@SetGroupControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface UpdateSetGroupExerciseController {

  @UpdateSetGroupExerciseDocumentation
  @PatchMapping("/setGroups/{setGroupId}/exercise")
  @ResponseStatus(HttpStatus.OK)
  UpdateSetGroupExerciseResponse execute(
      @PathVariable UUID setGroupId,
      @Valid @RequestBody UpdateSetGroupExerciseRequest updateSetGroupExerciseRequest)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException, ExerciseUnavailableException;
}
