package org.avillar.gymtracker.workoutapi.exercise.application.facade;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.GetExercisesByIdsService;
import org.avillar.gymtracker.workoutapi.exercise.application.mapper.GetExerciseFacadeMapper;
import org.avillar.gymtracker.workoutapi.exercise.application.model.GetExerciseResponseFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseRepositoryClientImpl implements ExerciseRepositoryClient {

  private final GetExercisesByIdsService getExercisesByIdsService;
  private final GetExerciseFacadeMapper getExerciseFacadeMapper;

  @Override
  public boolean canAccessExerciseById(UUID exerciseId) {
    return getExerciseFacadeMapper.getResponse(
            getExercisesByIdsService.execute(Collections.singleton(exerciseId)))
        != null;
  }

  @Override
  public List<GetExerciseResponseFacade> getExerciseByIds(Set<UUID> exerciseIds) {
    return getExerciseFacadeMapper.getResponse(getExercisesByIdsService.execute(exerciseIds));
  }
}
