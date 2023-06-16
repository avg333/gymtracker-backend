package org.avillar.gymtracker.workoutapi.exercise.application.facade;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.GetExerciseService;
import org.avillar.gymtracker.workoutapi.exercise.application.mapper.GetExerciseFacadeMapper;
import org.avillar.gymtracker.workoutapi.exercise.application.model.GetExerciseResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseRepositoryClientImpl implements ExerciseRepositoryClient {

  private final GetExerciseService getExerciseService;
  private final GetExerciseFacadeMapper getExerciseFacadeMapper;

  @Override
  public GetExerciseResponse getExerciseById(UUID exerciseId) {
    return getExerciseFacadeMapper.getResponse(getExerciseService.getById(exerciseId));
  }

  @Override
  public List<GetExerciseResponse> getExerciseByIds(Set<UUID> exerciseIds) {
    return getExerciseFacadeMapper.getResponse(getExerciseService.getByIds(exerciseIds));
  }
}
