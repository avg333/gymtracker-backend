package org.avillar.gymtracker.exercisesapi.exercise.deleteexercise.infrastructure;

import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.exercisesapi.exercise.ExerciseControllerDocumentation.ExerciseControllerTag;
import org.avillar.gymtracker.exercisesapi.exercise.deleteexercise.infrastructure.DeleteExerciseControllerDocumentation.Methods.DeleteExerciseDocumentation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@ExerciseControllerTag
@RequestMapping(path = "${exercisesApiPrefix}/v1")
public interface DeleteExerciseController {

  @DeleteExerciseDocumentation
  @DeleteMapping("exercises/{exerciseId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  Void execute(@PathVariable UUID exerciseId)
      throws ExerciseNotFoundException, ExerciseIllegalAccessException;
}
