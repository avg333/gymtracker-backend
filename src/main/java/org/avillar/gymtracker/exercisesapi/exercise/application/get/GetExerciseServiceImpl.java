package org.avillar.gymtracker.exercisesapi.exercise.application.get;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.mapper.GetExerciseServiceMapper;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.model.GetExerciseApplicationRequest;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.model.GetExerciseApplicationResponse;
import org.avillar.gymtracker.exercisesapi.exercise.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.exercise.domain.ExerciseDao;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetExerciseServiceImpl implements GetExerciseService {

  private final ExerciseDao exerciseDao;
  private final GetExerciseServiceMapper getExerciseServiceMapper;

  @Override
  public GetExerciseApplicationResponse getById(final UUID exerciseId) {
    return getExerciseServiceMapper.map(
        exerciseDao.getFullExerciseByIds(Set.of(exerciseId)).stream()
            .findAny()
            .orElseThrow(() -> new EntityNotFoundException(Exercise.class, exerciseId)));
  }

  @Override
  public List<GetExerciseApplicationResponse> getByIds(Set<UUID> exerciseIds) {
    return getExerciseServiceMapper.map(exerciseDao.getFullExerciseByIds(exerciseIds));
  }

  @Override
  public List<GetExerciseApplicationResponse> getAllExercises(
      GetExerciseApplicationRequest getExerciseApplicationRequest) {
    return getExerciseServiceMapper.map(exerciseDao.getAllFullExercises());
  }
}
