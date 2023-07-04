package org.avillar.gymtracker.workoutapi.exercise.application.facade;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.ExerciseNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.ExerciseNotFoundException.AccessError;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.GetExercisesByIdsService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.model.GetExercisesByIdsResponseApplication;
import org.avillar.gymtracker.workoutapi.exercise.application.mapper.GetExerciseFacadeMapperImpl;
import org.avillar.gymtracker.workoutapi.exercise.application.model.GetExerciseResponseFacade;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExerciseRepositoryClientImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private ExerciseRepositoryClientImpl exerciseRepositoryClient;

  @Mock private GetExercisesByIdsService getExercisesByIdsService;
  @Spy private GetExerciseFacadeMapperImpl getExerciseFacadeMapper;

  @Test
  void checkExerciseAccessByIdOk() {
    final UUID exerciseId = UUID.randomUUID();

    when(getExercisesByIdsService.execute(Set.of(exerciseId)))
        .thenReturn(List.of(easyRandom.nextObject(GetExercisesByIdsResponseApplication.class)));

    assertDoesNotThrow(() -> exerciseRepositoryClient.checkExerciseAccessById(exerciseId));
  }

  @Test
  void checkExerciseAccessByIdOkNotFound() {
    final UUID exerciseId = UUID.randomUUID();

    when(getExercisesByIdsService.execute(Set.of(exerciseId)))
        .thenThrow(new EntityNotFoundException(Exercise.class, exerciseId));

    final ExerciseNotFoundException exception =
        Assertions.assertThrows(
            ExerciseNotFoundException.class,
            () -> exerciseRepositoryClient.checkExerciseAccessById(exerciseId));
    assertEquals(exerciseId, exception.getId());
    assertEquals(AccessError.NOT_FOUND, exception.getAccessError());
  }

  @Test
  void checkExerciseAccessByIdOkNotAccess() {
    final GetExercisesByIdsResponseApplication exercise =
        easyRandom.nextObject(GetExercisesByIdsResponseApplication.class);

    when(getExercisesByIdsService.execute(Set.of(exercise.getId())))
        .thenThrow(
            new IllegalAccessException(
                mapExercise(exercise), AuthOperations.READ, UUID.randomUUID()));

    final ExerciseNotFoundException exception =
        Assertions.assertThrows(
            ExerciseNotFoundException.class,
            () -> exerciseRepositoryClient.checkExerciseAccessById(exercise.getId()));
    assertEquals(exercise.getId(), exception.getId());
    assertEquals(AccessError.NOT_ACCESS, exception.getAccessError());
  }

  @Test
  void getExercisesByIdsOk() {
    final List<GetExercisesByIdsResponseApplication> expected =
        easyRandom.objects(GetExercisesByIdsResponseApplication.class, 5).toList();
    final Set<UUID> exercisesIds =
        expected.stream()
            .map(GetExercisesByIdsResponseApplication::getId)
            .collect(Collectors.toSet());

    when(getExercisesByIdsService.execute(exercisesIds)).thenReturn(expected);

    final List<GetExerciseResponseFacade> result =
        assertDoesNotThrow(() -> exerciseRepositoryClient.getExerciseByIds(exercisesIds));
    assertEquals(expected.size(), result.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i).getId(), result.get(i).getId());
      assertEquals(expected.get(i).getName(), result.get(i).getName());
      assertEquals(expected.get(i).getDescription(), result.get(i).getDescription());
    } // TODO Comprobar el resto de valores
  }

  @Test
  void getExercisesByIdsNotAccess() {
    final List<GetExercisesByIdsResponseApplication> exercises =
        easyRandom.objects(GetExercisesByIdsResponseApplication.class, 5).toList();
    final Set<UUID> exercisesIds =
        exercises.stream()
            .map(GetExercisesByIdsResponseApplication::getId)
            .collect(Collectors.toSet());
    when(getExercisesByIdsService.execute(exercisesIds))
        .thenThrow(
            new IllegalAccessException(
                mapExercise(exercises.get(0)), AuthOperations.READ, UUID.randomUUID()));

    final ExerciseNotFoundException exception =
        Assertions.assertThrows(
            ExerciseNotFoundException.class,
            () -> exerciseRepositoryClient.getExerciseByIds(exercisesIds));
    assertEquals(exercises.get(0).getId(), exception.getId());
    assertEquals(AccessError.NOT_ACCESS, exception.getAccessError());
  }

  private Exercise mapExercise(
      GetExercisesByIdsResponseApplication getExercisesByIdsResponseApplication) {
    final Exercise exercise = new Exercise();
    exercise.setId(getExercisesByIdsResponseApplication.getId());

    return exercise;
  }
}
