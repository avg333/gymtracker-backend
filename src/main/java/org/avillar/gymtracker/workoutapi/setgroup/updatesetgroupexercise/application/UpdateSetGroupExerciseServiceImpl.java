package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.application;

import static java.util.Objects.nonNull;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.facade.exercise.ExercisesFacade;
import org.avillar.gymtracker.workoutapi.common.facade.setgroup.SetGroupFacade;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateSetGroupExerciseServiceImpl implements UpdateSetGroupExerciseService {

  private final SetGroupFacade setGroupFacade;
  private final ExercisesFacade exercisesFacade;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public UUID execute(final UUID setGroupId, final UUID exerciseId)
      throws SetGroupNotFoundException,
          WorkoutIllegalAccessException,
          ExerciseUnavailableException {
    final SetGroup setGroup = setGroupFacade.getSetGroupWithWorkout(setGroupId);

    authWorkoutsService.checkAccess(setGroup, AuthOperations.UPDATE);

    if (nonNull(setGroup.getExerciseId()) && setGroup.getExerciseId().equals(exerciseId)) {
      return exerciseId;
    }

    exercisesFacade.checkExerciseAccessById(exerciseId);

    try {
      exercisesFacade.swapExerciseUses(
          setGroup.getWorkout().getUserId(), exerciseId, setGroup.getExerciseId());
    } catch (ExerciseUnavailableException ex) {
      log.error("Error decrementing and incrementing exercise usage", ex);
    }

    setGroup.setExerciseId(exerciseId);

    return setGroupFacade.saveSetGroup(setGroup).getExerciseId();
  }
}
