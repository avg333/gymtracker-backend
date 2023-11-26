package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application;

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
import org.avillar.gymtracker.workoutapi.common.facade.setgroup.SetGroupFacade;
import org.avillar.gymtracker.workoutapi.common.facade.workout.WorkoutFacade;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateSetGroupServiceImpl implements CreateSetGroupService {

  private final SetGroupFacade setGroupFacade;
  private final WorkoutFacade workoutFacade;
  private final ExercisesFacade exercisesFacade;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public SetGroup execute(final UUID workoutId, final SetGroup setGroup)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException, ExerciseUnavailableException {
    final Workout workout = workoutFacade.getWorkoutWithSetGroups(workoutId);

    setGroup.setWorkout(workout);
    setGroup.setListOrder(workout.getSetGroups().size());

    authWorkoutsService.checkAccess(setGroup, AuthOperations.CREATE);

    exercisesFacade.checkExerciseAccessById(setGroup.getExerciseId());

    try {
      exercisesFacade.incrementExerciseUses(workout.getUserId(), setGroup.getExerciseId());
    } catch (ExerciseUnavailableException ex) {
      log.error("Error incrementing exercise usage", ex);
    }

    return setGroupFacade.saveSetGroup(setGroup);
  }
}
