package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.mapper.GetExercisesByFilterServiceMapperImpl;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model.GetExercisesByFilterRequestApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model.GetExercisesByFilterResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetExercisesByFilterServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetExercisesByFilterServiceImpl getExercisesByFilterService;

  @Mock private ExerciseDao exerciseDao;
  @Mock private AuthExercisesService authExercisesService;
  @Spy private GetExercisesByFilterServiceMapperImpl getExercisesByFilterServiceMapper;

  @Test
  void getOk() {
    final List<Exercise> expected = easyRandom.objects(Exercise.class, 5).toList();

    when(exerciseDao.getAllFullExercises()).thenReturn(expected);
    doNothing().when(authExercisesService).checkAccess(expected, AuthOperations.READ);

    final List<GetExercisesByFilterResponseApplication> result =
        getExercisesByFilterService.execute(
            easyRandom.nextObject(GetExercisesByFilterRequestApplication.class));
    assertEquals(expected.size(), result.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i).getId(), result.get(i).getId());
      assertEquals(expected.get(i).getName(), result.get(i).getName());
      assertEquals(expected.get(i).getDescription(), result.get(i).getDescription());
    }
  }

  @Test
  void getNotPermission() {
    final List<Exercise> exercises = easyRandom.objects(Exercise.class, 5).toList();
    final AuthOperations authOperation = AuthOperations.READ;
    final UUID userId = UUID.randomUUID();

    when(exerciseDao.getAllFullExercises()).thenReturn(exercises);
    doThrow(new IllegalAccessException(exercises.get(0), authOperation, userId))
        .when(authExercisesService)
        .checkAccess(exercises, authOperation);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class,
            () ->
                getExercisesByFilterService.execute(
                    easyRandom.nextObject(GetExercisesByFilterRequestApplication.class)));
    assertEquals(Exercise.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(exercises.get(0).getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(authOperation, exception.getAuthOperations());
  }
}
