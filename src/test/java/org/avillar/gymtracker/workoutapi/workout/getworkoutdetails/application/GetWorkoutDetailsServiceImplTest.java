package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.common.facade.exercise.ExercisesFacade;
import org.avillar.gymtracker.workoutapi.common.facade.workout.WorkoutFacade;
import org.avillar.gymtracker.workoutapi.common.utils.ExceptionGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class GetWorkoutDetailsServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.READ;

  @InjectMocks private GetWorkoutDetailsServiceImpl getWorkoutDetailsService;

  @Mock private WorkoutFacade workoutFacade;
  @Mock private ExercisesFacade exercisesFacade;
  @Mock private AuthWorkoutsService authWorkoutsService;

  private static Workout getWorkout() {
    final Workout workout = Instancio.create(Workout.class);
    final List<SetGroup> setGroups = Instancio.createList(SetGroup.class);
    for (int i = 0; i < setGroups.size(); i++) {
      final SetGroup setGroup = setGroups.get(i);
      setGroup.setListOrder(i);
      setGroup.setWorkout(workout);
      final List<Set> sets = Instancio.createList(Set.class);
      for (int j = 0; j < sets.size(); j++) {
        final Set set = sets.get(j);
        set.setListOrder(j);
        set.setSetGroup(setGroup);
      }
      setGroup.setSets(sets);
    }
    workout.setSetGroups(setGroups);
    return workout;
  }

  @Test
  void shouldReturnWorkoutDetailsSuccessfully()
      throws WorkoutNotFoundException, WorkoutIllegalAccessException, ExerciseUnavailableException {
    final Workout workout = getWorkout();
    final List<UUID> exerciseIds =
        workout.getSetGroups().stream().map(SetGroup::getExerciseId).toList();
    workout.setSetGroups(
        Instancio.ofList(SetGroup.class).size(workout.getSetGroups().size()).create());
    workout.getSetGroups().forEach(setGroup -> setGroup.setExercise(null));

    final List<Exercise> exercises =
        Instancio.ofList(Exercise.class).size(exerciseIds.size()).create();
    for (int i = 0; i < exerciseIds.size(); i++) {
      exercises.get(i).setId(exerciseIds.get(i));
      workout.getSetGroups().get(i).setExerciseId(exerciseIds.get(i));
    }

    when(workoutFacade.getFullWorkout(workout.getId())).thenReturn(workout);
    doNothing().when(authWorkoutsService).checkAccess(workout, AUTH_OPERATIONS);
    when(exercisesFacade.getExerciseByIds(new HashSet<>(exerciseIds))).thenReturn(exercises);

    final Workout result = getWorkoutDetailsService.execute(workout.getId());
    assertThat(result).isEqualTo(workout);

    result
        .getSetGroups()
        .forEach(
            setGroup ->
                assertThat(setGroup.getExercise())
                    .isEqualTo(
                        exercises.stream()
                            .filter(ex -> ex.getId().equals(setGroup.getExerciseId()))
                            .findAny()
                            .orElse(null)));
  }

  @Test
  void shouldReturnWorkoutDetailsWithoutExercisesWhenExercisesAreNotFound()
      throws WorkoutNotFoundException, WorkoutIllegalAccessException, ExerciseUnavailableException {
    final Workout workout = getWorkout();
    final List<UUID> exerciseIds =
        workout.getSetGroups().stream().map(SetGroup::getExerciseId).toList();
    workout.getSetGroups().forEach(setGroup -> setGroup.setExerciseId(null));
    final ExerciseUnavailableException exception =
        ExceptionGenerator.generateExerciseUnavailableException();

    when(workoutFacade.getFullWorkout(workout.getId())).thenReturn(workout);
    doNothing().when(authWorkoutsService).checkAccess(workout, AUTH_OPERATIONS);
    doThrow(exception).when(exercisesFacade).getExerciseByIds(new HashSet<>(exerciseIds));

    assertThat(getWorkoutDetailsService.execute(workout.getId())).isEqualTo(workout);
    workout.getSetGroups().forEach(setGroup -> assertThat(setGroup.getExercise()).isNull());
  }

  @Test
  void shouldThrowWorkoutIllegalAccessExceptionWhenUserHasNoPermissionToReadWorkout()
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {
    final Workout workout = Instancio.create(Workout.class);
    final WorkoutIllegalAccessException exception =
        ExceptionGenerator.generateWorkoutIllegalAccessException();

    when(workoutFacade.getFullWorkout(workout.getId())).thenReturn(workout);
    doThrow(exception).when(authWorkoutsService).checkAccess(workout, AUTH_OPERATIONS);

    assertThatThrownBy(() -> getWorkoutDetailsService.execute(workout.getId()))
        .isEqualTo(exception);
  }

  @Test
  void shouldThrowEntityNotFoundExceptionWhenGettingNonExistentWorkout()
      throws WorkoutNotFoundException {
    final UUID workoutId = UUID.randomUUID();
    final WorkoutNotFoundException exception =
        ExceptionGenerator.generateWorkoutNotFoundException();

    doThrow(exception).when(workoutFacade).getFullWorkout(workoutId);

    assertThatThrownBy(() -> getWorkoutDetailsService.execute(workoutId)).isEqualTo(exception);
  }
}
