package org.avillar.gymtracker.workoutapi.common.facade.exercise;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.domain.ExerciseUses;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.checkexercisereadaccess.application.CheckExerciseAccessService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.GetExercisesByIdsService;
import org.avillar.gymtracker.exercisesapi.exercise.modifyexerciseuses.application.ModifyExerciseUsesService;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException.AccessError;
import org.avillar.gymtracker.workoutapi.common.facade.exercise.model.DeleteExerciseUsesRequestFacade;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExercisesFacadeImpl implements ExercisesFacade {

  private static final int FIRST = 0;

  private final CheckExerciseAccessService checkExerciseAccessService;
  private final GetExercisesByIdsService getExercisesByIdsService;
  private final ModifyExerciseUsesService modifyExerciseUsesService;

  @Override
  public void checkExerciseAccessById(final UUID exerciseId) throws ExerciseUnavailableException {
    try {
      checkExerciseAccessService.execute(exerciseId, AuthOperations.READ);
    } catch (EntityNotFoundException ex) {
      throw new ExerciseUnavailableException(ex.getId(), AccessError.NOT_FOUND);
    } catch (IllegalAccessException ex) {
      throw new ExerciseUnavailableException(ex.getEntityId(), AccessError.NOT_ACCESS);
    }
  }

  @Override
  public List<Exercise> getExerciseByIds(final Set<UUID> exerciseIds)
      throws ExerciseUnavailableException {
    try {
      return getExercisesByIdsService.execute(exerciseIds);
    } catch (IllegalAccessException ex) {
      throw new ExerciseUnavailableException(ex.getEntityId(), AccessError.NOT_ACCESS);
    }
  }

  @Override
  public int incrementExerciseUses(UUID userId, UUID exerciseId)
      throws ExerciseUnavailableException {
    try {
      return modifyExerciseUsesService
          .execute(
              userId,
              List.of(
                  ExerciseUses.builder()
                      .uses(1)
                      .exercise(Exercise.builder().id(exerciseId).build())
                      .build()))
          .get(FIRST)
          .getUses();
    } catch (EntityNotFoundException ex) {
      throw new ExerciseUnavailableException(ex.getId(), AccessError.NOT_FOUND);
    } catch (ExerciseIllegalAccessException ex) {
      throw new ExerciseUnavailableException(ex.getEntityId(), AccessError.NOT_ACCESS);
    }
  }

  @Override
  public int decrementExerciseUses(UUID userId, UUID exerciseId)
      throws ExerciseUnavailableException {
    try {
      return modifyExerciseUsesService
          .execute(
              userId,
              List.of(
                  ExerciseUses.builder()
                      .uses(-1)
                      .exercise(Exercise.builder().id(exerciseId).build())
                      .build()))
          .get(FIRST)
          .getUses();
    } catch (EntityNotFoundException ex) {
      throw new ExerciseUnavailableException(ex.getId(), AccessError.NOT_FOUND);
    } catch (ExerciseIllegalAccessException ex) {
      throw new ExerciseUnavailableException(ex.getEntityId(), AccessError.NOT_ACCESS);
    }
  }

  @Override
  public void decrementExercisesUses(
      UUID userId, DeleteExerciseUsesRequestFacade deleteExerciseUsesRequestFacade)
      throws ExerciseUnavailableException {

    final List<ExerciseUses> modifyExerciseUsesRequestList =
        deleteExerciseUsesRequestFacade.getExerciseUses().stream()
            .map(
                exerciseUses ->
                    ExerciseUses.builder()
                        .exercise(Exercise.builder().id(exerciseUses.getExerciseId()).build())
                        .uses(-exerciseUses.getUses())
                        .build())
            .toList();

    try {
      modifyExerciseUsesService.execute(userId, modifyExerciseUsesRequestList);
    } catch (EntityNotFoundException ex) {
      throw new ExerciseUnavailableException(ex.getId(), AccessError.NOT_FOUND);
    } catch (ExerciseIllegalAccessException ex) {
      throw new ExerciseUnavailableException(ex.getEntityId(), AccessError.NOT_ACCESS);
    }
  }

  @Override
  public int swapExerciseUses(final UUID userId, final UUID newExerciseId, final UUID oldExerciseId)
      throws ExerciseUnavailableException {
    final ExerciseUses incrementUses =
        ExerciseUses.builder()
            .exercise(Exercise.builder().id(newExerciseId).build())
            .uses(1)
            .build();
    final ExerciseUses decrementUses =
        ExerciseUses.builder()
            .exercise(Exercise.builder().id(oldExerciseId).build())
            .uses(-1)
            .build();

    try {
      modifyExerciseUsesService.execute(userId, List.of(incrementUses, decrementUses));
      return 0;
    } catch (EntityNotFoundException ex) {
      throw new ExerciseUnavailableException(ex.getId(), AccessError.NOT_FOUND);
    } catch (ExerciseIllegalAccessException ex) {
      throw new ExerciseUnavailableException(ex.getEntityId(), AccessError.NOT_ACCESS);
    }
  }
}
