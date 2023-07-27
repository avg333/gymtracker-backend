package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
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
      throws EntityNotFoundException, IllegalAccessException {
    return getExerciseByIdControllerMapper.map(getExerciseByIdService.execute(exerciseId));
  }
}
