package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.ExerciseControllerDocumentation.ExerciseControllerTag;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.GetExercisesByFilterControllerDocumentation.Methods.GetExercisesByFilterDocumentation;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterRequest;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@ExerciseControllerTag
@RequestMapping(path = "${exercisesApiPrefix}/v1")
public interface GetExercisesByFilterController {

  @GetExercisesByFilterDocumentation
  @GetMapping("exercises/filter")
  @ResponseStatus(HttpStatus.OK)
  List<GetExercisesByFilterResponse> execute(
      GetExercisesByFilterRequest getExercisesByFilterRequest)
      throws ExerciseIllegalAccessException;
}
