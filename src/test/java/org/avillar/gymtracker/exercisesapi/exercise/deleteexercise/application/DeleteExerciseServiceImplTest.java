// package org.avillar.gymtracker.exercisesapi.exercise.deleteexercise.application;
//
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.doNothing;
// import static org.mockito.Mockito.doThrow;
// import static org.mockito.Mockito.never;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;
//
// import java.util.Collections;
// import java.util.HashSet;
// import java.util.List;
// import java.util.UUID;
// import java.util.stream.Collectors;
// import org.avillar.gymtracker.common.base.domain.BaseEntity;
// import org.avillar.gymtracker.common.errors.application.AuthOperations;
// import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
// import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
// import org.avillar.gymtracker.exercisesapi.common.auth.application.AuthExercisesService;
// import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
// import org.avillar.gymtracker.exercisesapi.common.adapter.repository.ExerciseDao;
// import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroupExercise;
// import org.avillar.gymtracker.exercisesapi.common.adapter.repository.MuscleGroupExerciseDao;
// import org.avillar.gymtracker.exercisesapi.common.exception.application.DeleteExerciseException;
// import
// org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
// import org.avillar.gymtracker.exercisesapi.common.facade.workout.WorkoutFacade;
// import
// org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
// import org.instancio.Instancio;
//
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.junit.jupiter.api.parallel.Execution;
// import org.junit.jupiter.api.parallel.ExecutionMode;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
//
// @Execution(ExecutionMode.CONCURRENT)
// @ExtendWith(MockitoExtension.class)
// class DeleteExerciseServiceImplTest {
//
//  private static final int LIST_SIZE = 5;
//
//  private final EasyRandom easyRandom = new EasyRandom();
//
//  @InjectMocks private DeleteExerciseServiceImpl deleteExerciseService;
//
//  @Mock private ExerciseDao exerciseDao;
//  @Mock private MuscleGroupExerciseDao muscleGroupExerciseDao;
//  @Mock private AuthExercisesService authExercisesService;
//  @Mock private WorkoutFacade workoutFacade;
//
//  @Test
//  void deleteOk() throws WorkoutIllegalAccessException, ExerciseIllegalAccessException {
//    final Exercise exercise = Instancio.create(Exercise.class);
//    exercise.setMuscleGroupExercises(
//        easyRandom.objects(MuscleGroupExercise.class, LIST_SIZE).collect(Collectors.toSet()));
//
//    when(exerciseDao.getExerciseByIdWithMuscleGroupEx(exercise.getId()))
//        .thenReturn(List.of(exercise));
//    doNothing().when(authExercisesService).checkAccess(exercise, AuthOperations.DELETE);
//    when(workoutFacade.getExerciseUsesNumberForUser(
//            exercise.getId(), exercise.getOwner()))
//        .thenReturn(0);
//
//    assertDoesNotThrow(() -> deleteExerciseService.execute(exercise.getId()));
//    verify(muscleGroupExerciseDao)
//        .deleteAllById(
//            exercise.getMuscleGroupExercises().stream()
//                .map(BaseEntity::getId)
//                .collect(Collectors.toSet()));
//    verify(exerciseDao).deleteById(exercise.getId());
//  }
//
//  @Test
//  void deleteOkNoMGE() throws ExerciseIllegalAccessException, WorkoutIllegalAccessException {
//    final Exercise exercise = Instancio.create(Exercise.class);
//    exercise.setMuscleGroupExercises(new HashSet<>());
//
//    when(exerciseDao.getExerciseByIdWithMuscleGroupEx(exercise.getId()))
//        .thenReturn(List.of(exercise));
//    doNothing().when(authExercisesService).checkAccess(exercise, AuthOperations.DELETE);
//    when(workoutFacade.getExerciseUsesNumberForUser(
//            exercise.getId(), exercise.getOwner()))
//        .thenReturn(0);
//
//    assertDoesNotThrow(() -> deleteExerciseService.execute(exercise.getId()));
//    verify(muscleGroupExerciseDao, never()).deleteAllById(any());
//    verify(exerciseDao).deleteById(exercise.getId());
//  }
//
//  @Test
//  void workoutNotFound() {
//    final UUID exerciseId = UUID.randomUUID();
//
//    when(exerciseDao.getExerciseByIdWithMuscleGroupEx(exerciseId))
//        .thenReturn(Collections.emptyList());
//
//    final EntityNotFoundException exception =
//        assertThrows(
//            EntityNotFoundException.class, () -> deleteExerciseService.execute(exerciseId));
//    assertEquals(Exercise.class.getSimpleName(), exception.getClassName());
//    assertEquals(exerciseId, exception.getId());
//    verify(muscleGroupExerciseDao, never()).deleteAllById(any());
//    verify(exerciseDao, never()).deleteById(any());
//  }
//
//  @Test
//  void deleteNotPermission() throws ExerciseIllegalAccessException {
//    final Exercise exercise = Instancio.create(Exercise.class);
//    exercise.setMuscleGroupExercises(
//        easyRandom.objects(MuscleGroupExercise.class, LIST_SIZE).collect(Collectors.toSet()));
//    final UUID exerciseId = exercise.getId();
//    final UUID userId = UUID.randomUUID();
//    final AuthOperations deleteOperation = AuthOperations.DELETE;
//
//    when(exerciseDao.getExerciseByIdWithMuscleGroupEx(exerciseId)).thenReturn(List.of(exercise));
//    doThrow(new IllegalAccessException(exercise, deleteOperation, userId))
//        .when(authExercisesService)
//        .checkAccess(exercise, deleteOperation);
//
//    final IllegalAccessException exception =
//        assertThrows(IllegalAccessException.class, () ->
// deleteExerciseService.execute(exerciseId));
//    assertEquals(Exercise.class.getSimpleName(), exception.getEntityClassName());
//    assertEquals(exerciseId, exception.getEntityId());
//    assertEquals(userId, exception.getCurrentUserId());
//    assertEquals(deleteOperation, exception.getAuthOperations());
//    verify(muscleGroupExerciseDao, never()).deleteAllById(any());
//    verify(exerciseDao, never()).deleteById(any());
//  }
//
//  @Test
//  void deleteKoUsedPermission()
//      throws WorkoutIllegalAccessException, ExerciseIllegalAccessException {
//    final Exercise exercise = Instancio.create(Exercise.class);
//    exercise.setMuscleGroupExercises(
//        easyRandom.objects(MuscleGroupExercise.class, LIST_SIZE).collect(Collectors.toSet()));
//    final UUID exerciseId = exercise.getId();
//
//    when(exerciseDao.getExerciseByIdWithMuscleGroupEx(exerciseId)).thenReturn(List.of(exercise));
//    doNothing().when(authExercisesService).checkAccess(exercise, AuthOperations.DELETE);
//    when(workoutFacade.getExerciseUsesNumberForUser(exerciseId, exercise.getOwner()))
//        .thenReturn(1);
//
//    assertThrows(DeleteExerciseException.class, () -> deleteExerciseService.execute(exerciseId));
//    verify(muscleGroupExerciseDao, never()).deleteAllById(any());
//    verify(exerciseDao, never()).deleteById(any());
//  }
// }
