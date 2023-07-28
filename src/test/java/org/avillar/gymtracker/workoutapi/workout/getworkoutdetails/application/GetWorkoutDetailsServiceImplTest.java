package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.avillar.gymtracker.workoutapi.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.workoutapi.exception.application.ExerciseNotFoundException.AccessError;
import org.avillar.gymtracker.workoutapi.exercise.application.facade.ExerciseRepositoryClient;
import org.avillar.gymtracker.workoutapi.exercise.application.model.GetExerciseResponseFacade;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.mapper.GetWorkoutDetailsServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.model.GetWorkoutDetailsResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetWorkoutDetailsServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetWorkoutDetailsServiceImpl getWorkoutDetailsService;

  @Mock private WorkoutDao workoutDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Mock private ExerciseRepositoryClient exerciseRepositoryClient;
  @Spy private GetWorkoutDetailsServiceMapperImpl getWorkoutDetailsServiceMapper;

  @Test
  void getOk() {
    final Workout workout = getWorkout();
    final List<UUID> exerciseIds =
        workout.getSetGroups().stream().map(SetGroup::getExerciseId).toList();
    final List<GetExerciseResponseFacade> exercises =
        easyRandom.objects(GetExerciseResponseFacade.class, exerciseIds.size()).toList();
    for (int i = 0; i < exerciseIds.size(); i++) {
      exercises.get(i).setId(exerciseIds.get(i));
    }

    when(workoutDao.getFullWorkoutByIds(List.of(workout.getId()))).thenReturn(List.of(workout));
    doNothing().when(authWorkoutsService).checkAccess(workout, AuthOperations.READ);
    when(exerciseRepositoryClient.getExerciseByIds(new HashSet<>(exerciseIds)))
        .thenReturn(exercises);

    final GetWorkoutDetailsResponseApplication result =
        getWorkoutDetailsService.execute(workout.getId());
    //  assertThat(result).usingRecursiveComparison().isEqualTo(workout); FIXME
    for (final var setGroup : result.getSetGroups()) {
      final Optional<GetExerciseResponseFacade> exercise =
          exercises.stream()
              .filter(
                  getExerciseResponseFacade ->
                      getExerciseResponseFacade.getId().equals(setGroup.getExercise().getId()))
              .findAny();
      assertThat(exercise).isNotNull();
      assertThat(setGroup.getExercise()).usingRecursiveComparison().isEqualTo(exercise.get());
    }
  }

  @Test
  void getOkNoExercises() {
    final Workout workout = getWorkout();
    final List<UUID> exerciseIds =
        workout.getSetGroups().stream().map(SetGroup::getExerciseId).toList();

    when(workoutDao.getFullWorkoutByIds(List.of(workout.getId()))).thenReturn(List.of(workout));
    doNothing().when(authWorkoutsService).checkAccess(workout, AuthOperations.READ);
    doThrow(new ExerciseNotFoundException(exerciseIds.get(0), AccessError.NOT_ACCESS))
        .when(exerciseRepositoryClient)
        .getExerciseByIds(new HashSet<>(exerciseIds));

    final GetWorkoutDetailsResponseApplication result =
        getWorkoutDetailsService.execute(workout.getId());

    // assertThat(result).usingRecursiveComparison().isEqualTo(workout); FIXME
    for (final var setGroup : result.getSetGroups()) {
      assertThat(setGroup.getExercise()).isNull();
    }
  }

  private Workout getWorkout() {
    final Workout workout = easyRandom.nextObject(Workout.class);
    final List<SetGroup> setGroups = easyRandom.objects(SetGroup.class, 5).toList();
    for (int i = 0; i < setGroups.size(); i++) {
      final SetGroup setGroup = setGroups.get(i);
      setGroup.setListOrder(i);
      setGroup.setWorkout(workout);
      final List<Set> sets = easyRandom.objects(Set.class, 5).toList();
      for (int j = 0; j < sets.size(); j++) {
        final Set set = sets.get(j);
        set.setListOrder(j);
        set.setSetGroup(setGroup);
      }
      setGroup.setSets(new HashSet<>(sets));
    }
    workout.setSetGroups(new HashSet<>(setGroups));
    return workout;
  }

  @Test
  void getNotFound() {
    final UUID workoutId = UUID.randomUUID();

    when(workoutDao.getFullWorkoutByIds(List.of(workoutId))).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        assertThrows(
            EntityNotFoundException.class, () -> getWorkoutDetailsService.execute(workoutId));
    assertEquals(Workout.class.getSimpleName(), exception.getClassName());
    assertEquals(workoutId, exception.getId());
  }

  @Test
  void getNotPermission() {
    final Workout workout = easyRandom.nextObject(Workout.class);
    final UUID userId = UUID.randomUUID();
    final AuthOperations readOperation = AuthOperations.READ;

    when(workoutDao.getFullWorkoutByIds(List.of(workout.getId()))).thenReturn(List.of(workout));
    doThrow(new IllegalAccessException(workout, readOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(workout, readOperation);

    final IllegalAccessException exception =
        assertThrows(
            IllegalAccessException.class, () -> getWorkoutDetailsService.execute(workout.getId()));
    assertEquals(Workout.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(workout.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(readOperation, exception.getAuthOperations());
  }
}
