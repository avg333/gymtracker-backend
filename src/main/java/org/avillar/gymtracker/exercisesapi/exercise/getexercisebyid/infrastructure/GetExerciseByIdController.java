package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure;

import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.exercisesapi.exercise.ExerciseControllerDocumentation.ExerciseControllerTag;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.GetExerciseByIdControllerDocumentation.Methods.GetExerciseByIdDocumentation;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.model.GetExerciseByIdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@ExerciseControllerTag
@RequestMapping(path = "${exercisesApiPrefix}/v1")
public interface GetExerciseByIdController {

  @GetExerciseByIdDocumentation
  @GetMapping("exercises/{exerciseId}")
  @ResponseStatus(HttpStatus.OK)
  GetExerciseByIdResponse execute(@PathVariable UUID exerciseId)
      throws ExerciseNotFoundException, ExerciseIllegalAccessException;
}
