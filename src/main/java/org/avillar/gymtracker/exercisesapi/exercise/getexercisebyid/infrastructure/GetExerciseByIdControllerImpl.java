package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.GetExerciseByIdService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.mapper.GetExerciseByIdControllerMapper;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.model.GetExerciseByIdResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GetExerciseByIdControllerImpl implements GetExerciseByIdController {

  private final GetExerciseByIdService getExerciseByIdService;
  private final GetExerciseByIdControllerMapper getExerciseByIdControllerMapper;

  @Override
  public GetExerciseByIdResponse execute(final UUID exerciseId)
      throws ExerciseNotFoundException, ExerciseIllegalAccessException {
    return getExerciseByIdControllerMapper.map(getExerciseByIdService.execute(exerciseId));
  }
}
