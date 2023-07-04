package org.avillar.gymtracker.workoutsapi.exercise.application.facade;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.ExerciseNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.ExerciseNotFoundException.AccessError;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
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
  public void checkExerciseAccessById(UUID exerciseId)
      throws ExerciseNotFoundException, IllegalAccessException {
    try {
      getExercisesByIdsService.execute(Collections.singleton(exerciseId));
    } catch (EntityNotFoundException ex) {
      throw new ExerciseNotFoundException(ex.getId(), AccessError.NOT_FOUND);
    } catch (IllegalAccessException ex) {
      throw new ExerciseNotFoundException(ex.getEntityId(), AccessError.NOT_ACCESS);
    }
  }

  @Override
  public List<GetExerciseResponseFacade> getExerciseByIds(Set<UUID> exerciseIds)
      throws IllegalAccessException {
    try {
      return getExerciseFacadeMapper.getResponse(getExercisesByIdsService.execute(exerciseIds));
    } catch (IllegalAccessException ex) {
      throw new ExerciseNotFoundException(ex.getEntityId(), AccessError.NOT_ACCESS);
    }
  }
}
