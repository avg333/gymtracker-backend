package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.GetExercisesByFilterService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.mapper.GetExercisesByFilterControllerMapper;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterRequest;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GetExercisesByFilterControllerImpl implements GetExercisesByFilterController {

  private final GetExercisesByFilterService getExercisesByFilterService;
  private final GetExercisesByFilterControllerMapper getExercisesByFilterControllerMapper;

  @Override
  public List<GetExercisesByFilterResponse> execute(
      final GetExercisesByFilterRequest getExercisesByFilterRequest)
      throws ExerciseIllegalAccessException {
    return getExercisesByFilterControllerMapper.map(
        getExercisesByFilterService.execute(
            getExercisesByFilterControllerMapper.map(getExercisesByFilterRequest)));
  }
}
