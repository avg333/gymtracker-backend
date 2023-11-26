package org.avillar.gymtracker.workoutapi.workout.deleteworkout.application;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.common.facade.exercise.ExercisesFacade;
import org.avillar.gymtracker.workoutapi.common.facade.exercise.model.DeleteExerciseUsesRequestFacade;
import org.avillar.gymtracker.workoutapi.common.facade.exercise.model.DeleteExerciseUsesRequestFacade.ExerciseUses;
import org.avillar.gymtracker.workoutapi.common.facade.workout.WorkoutFacade;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteWorkoutServiceImpl implements DeleteWorkoutService {

  private final WorkoutFacade workoutFacade;
  private final ExercisesFacade exercisesFacade;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public void execute(final UUID workoutId)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {
    final Workout workout = workoutFacade.getWorkoutWithSetGroups(workoutId);

    authWorkoutsService.checkAccess(workout, AuthOperations.DELETE);

    decrementExerciseUsages(workout.getUserId(), workout.getSetGroups());

    workoutFacade.deleteWorkout(workoutId);
  }

  private void decrementExerciseUsages(final UUID userId, final List<SetGroup> setGroups) {
    final List<UUID> exerciseIds =
        setGroups.stream().map(SetGroup::getExerciseId).filter(Objects::nonNull).toList();
    log.debug("Decrementing usages for exercises {}", exerciseIds);

    try {
      exercisesFacade.decrementExercisesUses(
          userId,
          DeleteExerciseUsesRequestFacade.builder()
              .exerciseUses(
                  exerciseIds.stream()
                      .map(exId -> ExerciseUses.builder().exerciseId(exId).uses(1).build())
                      .toList())
              .build());
    } catch (ExerciseUnavailableException ex) {
      log.error("Error decrementing exercise uses", ex);
    }
  }
}
