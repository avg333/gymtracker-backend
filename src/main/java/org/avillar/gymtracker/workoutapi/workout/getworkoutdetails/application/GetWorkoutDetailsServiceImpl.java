package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.common.facade.exercise.ExercisesFacade;
import org.avillar.gymtracker.workoutapi.common.facade.workout.WorkoutFacade;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetWorkoutDetailsServiceImpl implements GetWorkoutDetailsService {

  private final WorkoutFacade workoutFacade;
  private final ExercisesFacade exercisesFacade;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public Workout execute(final UUID workoutId)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {
    final Workout workout = workoutFacade.getFullWorkout(workoutId);

    authWorkoutsService.checkAccess(workout, AuthOperations.READ);

    final List<Exercise> exercises =
        getExercisesFromRepository(
            workout.getSetGroups().stream()
                .map(SetGroup::getExerciseId)
                .collect(Collectors.toSet()));

    populateResponseWithExercises(exercises, workout);

    return workout; // TODO Avoid return msupg and more
  }

  private List<Exercise> getExercisesFromRepository(final Set<UUID> exerciseIds) {
    try {
      return exercisesFacade.getExerciseByIds(exerciseIds);
    } catch (Exception e) {
      log.error("Error retrieving exercises from the repository. Ids: {}", exerciseIds, e);
    }
    return Collections.emptyList();
  }

  private void populateResponseWithExercises(
      final List<Exercise> getExerciseResponseFacades, final Workout getWorkoutDetailsResponse) {
    getWorkoutDetailsResponse
        .getSetGroups()
        .forEach(
            setGroup ->
                setGroup.setExercise(
                    getExerciseResponseFacades.stream()
                        .filter(ex -> ex.getId().equals(setGroup.getExerciseId()))
                        .findAny()
                        .orElse(null)));
  }
}
