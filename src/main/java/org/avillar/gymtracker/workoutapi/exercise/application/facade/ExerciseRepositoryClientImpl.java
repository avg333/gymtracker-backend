package org.avillar.gymtracker.workoutapi.exercise.application.facade;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.checkexercisereadaccess.application.CheckExerciseReadAccessService;
import org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.application.DecrementExerciseUsesService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.GetExercisesByIdsService;
import org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.application.IncrementExerciseUsesService;
import org.avillar.gymtracker.workoutapi.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.workoutapi.exception.application.ExerciseNotFoundException.AccessError;
import org.avillar.gymtracker.workoutapi.exercise.application.mapper.GetExerciseFacadeMapper;
import org.avillar.gymtracker.workoutapi.exercise.application.model.GetExerciseResponseFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseRepositoryClientImpl implements ExerciseRepositoryClient {

  private final CheckExerciseReadAccessService checkExerciseReadAccessService;
  private final GetExercisesByIdsService getExercisesByIdsService;
  private final GetExerciseFacadeMapper getExerciseFacadeMapper;
  private final IncrementExerciseUsesService incrementExerciseUsesService;
  private final DecrementExerciseUsesService decrementExerciseUsesService;

  @Override
  public void checkExerciseAccessById(final UUID exerciseId) throws ExerciseNotFoundException {
    try {
      checkExerciseReadAccessService.execute(exerciseId);
    } catch (EntityNotFoundException ex) {
      throw new ExerciseNotFoundException(ex.getId(), AccessError.NOT_FOUND);
    } catch (IllegalAccessException ex) {
      throw new ExerciseNotFoundException(ex.getEntityId(), AccessError.NOT_ACCESS);
    }
  }

  @Override
  public List<GetExerciseResponseFacade> getExerciseByIds(final Set<UUID> exerciseIds)
      throws ExerciseNotFoundException {
    try {
      return getExerciseFacadeMapper.map(getExercisesByIdsService.execute(exerciseIds));
    } catch (IllegalAccessException ex) {
      throw new ExerciseNotFoundException(ex.getEntityId(), AccessError.NOT_ACCESS);
    }
  }

  @Override
  public int incrementExerciseUses(UUID exerciseId) throws ExerciseNotFoundException {
    try {
      return incrementExerciseUsesService.execute(exerciseId).getUses();
    } catch (EntityNotFoundException ex) {
      throw new ExerciseNotFoundException(ex.getId(), AccessError.NOT_FOUND);
    } catch (IllegalAccessException ex) {
      throw new ExerciseNotFoundException(ex.getEntityId(), AccessError.NOT_ACCESS);
    }
  }

  @Override
  public int decrementExerciseUses(UUID exerciseId) throws ExerciseNotFoundException {
    try {
      return decrementExerciseUsesService.execute(exerciseId).getUses();
    } catch (EntityNotFoundException ex) {
      throw new ExerciseNotFoundException(ex.getId(), AccessError.NOT_FOUND);
    } catch (IllegalAccessException ex) {
      throw new ExerciseNotFoundException(ex.getEntityId(), AccessError.NOT_ACCESS);
    }
  }
}
