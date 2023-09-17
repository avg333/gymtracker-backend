package org.avillar.gymtracker.workoutapi.exercise.application.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.exercise.checkexercisereadaccess.application.CheckExerciseReadAccessService;
import org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.application.DecrementExerciseUsesService;
import org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.application.model.DecrementExerciseUsesResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.GetExercisesByIdsService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.model.GetExercisesByIdsResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.application.IncrementExerciseUsesService;
import org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.application.model.IncrementExerciseUsesResponseApplication;
import org.avillar.gymtracker.workoutapi.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.workoutapi.exception.application.ExerciseNotFoundException.AccessError;
import org.avillar.gymtracker.workoutapi.exercise.application.mapper.GetExerciseFacadeMapper;
import org.avillar.gymtracker.workoutapi.exercise.application.model.GetExerciseResponseFacade;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class ExerciseRepositoryClientImplTest {

  private static final int LIST_SIZE = 5;

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private ExerciseRepositoryClientImpl exerciseRepositoryClient;

  @Mock private CheckExerciseReadAccessService checkExerciseReadAccessService;
  @Mock private GetExercisesByIdsService getExercisesByIdsService;
  @Mock private IncrementExerciseUsesService incrementExerciseUsesService;
  @Mock private DecrementExerciseUsesService decrementExerciseUsesService;

  @Spy
  private final GetExerciseFacadeMapper getExerciseFacadeMapper =
      Mappers.getMapper(GetExerciseFacadeMapper.class);

  @Test
  void checkExerciseAccessByIdOk() {
    final UUID exerciseId = UUID.randomUUID();

    doNothing().when(checkExerciseReadAccessService).execute(exerciseId);

    assertDoesNotThrow(() -> exerciseRepositoryClient.checkExerciseAccessById(exerciseId));
  }

  @Test
  void checkExerciseAccessByIdOkNotFound() {
    final UUID exerciseId = UUID.randomUUID();

    doThrow(new EntityNotFoundException(Exercise.class, exerciseId))
        .when(checkExerciseReadAccessService)
        .execute(exerciseId);

    final ExerciseNotFoundException exception =
        assertThrows(
            ExerciseNotFoundException.class,
            () -> exerciseRepositoryClient.checkExerciseAccessById(exerciseId));
    assertEquals(exerciseId, exception.getId());
    assertEquals(AccessError.NOT_FOUND, exception.getAccessError());
  }

  @Test
  void checkExerciseAccessByIdOkNotAccess() {
    final GetExercisesByIdsResponseApplication exercise =
        easyRandom.nextObject(GetExercisesByIdsResponseApplication.class);

    doThrow(
            new IllegalAccessException(
                mapExercise(exercise), AuthOperations.READ, UUID.randomUUID()))
        .when(checkExerciseReadAccessService)
        .execute(exercise.getId());

    final ExerciseNotFoundException exception =
        assertThrows(
            ExerciseNotFoundException.class,
            () -> exerciseRepositoryClient.checkExerciseAccessById(exercise.getId()));
    assertEquals(exercise.getId(), exception.getId());
    assertEquals(AccessError.NOT_ACCESS, exception.getAccessError());
  }

  @Test
  void getExercisesByIdsOk() {
    final List<GetExercisesByIdsResponseApplication> expected =
        easyRandom.objects(GetExercisesByIdsResponseApplication.class, LIST_SIZE).toList();
    final Set<UUID> exercisesIds =
        expected.stream()
            .map(GetExercisesByIdsResponseApplication::getId)
            .collect(Collectors.toSet());

    when(getExercisesByIdsService.execute(exercisesIds)).thenReturn(expected);

    final List<GetExerciseResponseFacade> result =
        assertDoesNotThrow(() -> exerciseRepositoryClient.getExerciseByIds(exercisesIds));
    assertThat(result).hasSameSizeAs(expected);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void getExercisesByIdsNotAccess() {
    final List<GetExercisesByIdsResponseApplication> exercises =
        easyRandom.objects(GetExercisesByIdsResponseApplication.class, LIST_SIZE).toList();
    final Set<UUID> exercisesIds =
        exercises.stream()
            .map(GetExercisesByIdsResponseApplication::getId)
            .collect(Collectors.toSet());
    final GetExercisesByIdsResponseApplication exerciseNoAccess = exercises.get(0);

    when(getExercisesByIdsService.execute(exercisesIds))
        .thenThrow(
            new IllegalAccessException(
                mapExercise(exerciseNoAccess), AuthOperations.READ, UUID.randomUUID()));

    final ExerciseNotFoundException exception =
        assertThrows(
            ExerciseNotFoundException.class,
            () -> exerciseRepositoryClient.getExerciseByIds(exercisesIds));
    assertEquals(exerciseNoAccess.getId(), exception.getId());
    assertEquals(AccessError.NOT_ACCESS, exception.getAccessError());
  }

  @Test
  void incrementExerciseUsesOk() {
    final IncrementExerciseUsesResponseApplication expected =
        easyRandom.nextObject(IncrementExerciseUsesResponseApplication.class);
    final UUID exerciseId = easyRandom.nextObject(UUID.class);

    when(incrementExerciseUsesService.execute(exerciseId)).thenReturn(expected);

    final int result =
        assertDoesNotThrow(() -> exerciseRepositoryClient.incrementExerciseUses(exerciseId));
    assertThat(result).isEqualTo(expected.getUses());
  }

  @Test
  void incrementExerciseUsesNotFound() {
    final UUID exerciseId = easyRandom.nextObject(UUID.class);

    doThrow(new EntityNotFoundException(Exercise.class, exerciseId))
        .when(incrementExerciseUsesService)
        .execute(exerciseId);

    final ExerciseNotFoundException exception =
        assertThrows(
            ExerciseNotFoundException.class,
            () -> exerciseRepositoryClient.incrementExerciseUses(exerciseId));
    assertEquals(exerciseId, exception.getId());
    assertEquals(AccessError.NOT_FOUND, exception.getAccessError());
  }

  @Test
  void incrementExerciseUsesNotAccess() {
    final UUID exerciseId = easyRandom.nextObject(UUID.class);
    final Exercise exercise = easyRandom.nextObject(Exercise.class);
    exercise.setId(exerciseId);

    doThrow(new IllegalAccessException(exercise, AuthOperations.READ, UUID.randomUUID()))
        .when(incrementExerciseUsesService)
        .execute(exerciseId);

    final ExerciseNotFoundException exception =
        assertThrows(
            ExerciseNotFoundException.class,
            () -> exerciseRepositoryClient.incrementExerciseUses(exerciseId));
    assertEquals(exerciseId, exception.getId());
    assertEquals(AccessError.NOT_ACCESS, exception.getAccessError());
  }

  @Test
  void decrementExerciseUsesOk() {
    final DecrementExerciseUsesResponseApplication expected =
        easyRandom.nextObject(DecrementExerciseUsesResponseApplication.class);
    final UUID exerciseId = easyRandom.nextObject(UUID.class);

    when(decrementExerciseUsesService.execute(exerciseId)).thenReturn(expected);

    final int result =
        assertDoesNotThrow(() -> exerciseRepositoryClient.decrementExerciseUses(exerciseId));
    assertThat(result).isEqualTo(expected.getUses());
  }

  @Test
  void decrementExerciseUsesNotFound() {
    final UUID exerciseId = easyRandom.nextObject(UUID.class);

    doThrow(new EntityNotFoundException(Exercise.class, exerciseId))
        .when(decrementExerciseUsesService)
        .execute(exerciseId);

    final ExerciseNotFoundException exception =
        assertThrows(
            ExerciseNotFoundException.class,
            () -> exerciseRepositoryClient.decrementExerciseUses(exerciseId));
    assertEquals(exerciseId, exception.getId());
    assertEquals(AccessError.NOT_FOUND, exception.getAccessError());
  }

  @Test
  void decrementExerciseUsesNotAccess() {
    final UUID exerciseId = easyRandom.nextObject(UUID.class);
    final Exercise exercise = easyRandom.nextObject(Exercise.class);
    exercise.setId(exerciseId);

    doThrow(new IllegalAccessException(exercise, AuthOperations.READ, UUID.randomUUID()))
        .when(decrementExerciseUsesService)
        .execute(exerciseId);

    final ExerciseNotFoundException exception =
        assertThrows(
            ExerciseNotFoundException.class,
            () -> exerciseRepositoryClient.decrementExerciseUses(exerciseId));
    assertEquals(exerciseId, exception.getId());
    assertEquals(AccessError.NOT_ACCESS, exception.getAccessError());
  }

  private Exercise mapExercise(
      final GetExercisesByIdsResponseApplication getExercisesByIdsResponseApplication) {
    final Exercise exercise = new Exercise();
    exercise.setId(getExercisesByIdsResponseApplication.getId());

    return exercise;
  }
}
