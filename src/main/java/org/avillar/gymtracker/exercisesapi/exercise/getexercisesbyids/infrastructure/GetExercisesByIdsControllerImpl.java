package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.GetExercisesByIdsService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure.mapper.GetExercisesByIdsControllerMapper;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure.model.GetExercisesByIdsResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GetExercisesByIdsControllerImpl implements GetExercisesByIdsController {

  private final GetExercisesByIdsService getExercisesByIdsService;
  private final GetExercisesByIdsControllerMapper getExercisesByIdsControllerMapper;

  @Override
  public List<GetExercisesByIdsResponse> execute(final Set<UUID> exerciseIds)
      throws IllegalAccessException {
    return getExercisesByIdsControllerMapper.map(getExercisesByIdsService.execute(exerciseIds));
  }
}
