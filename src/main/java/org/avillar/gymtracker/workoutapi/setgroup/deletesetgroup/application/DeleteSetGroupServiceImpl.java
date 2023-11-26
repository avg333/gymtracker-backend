package org.avillar.gymtracker.workoutapi.setgroup.deletesetgroup.application;

import jakarta.transaction.Transactional;
import java.util.List;
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
import org.avillar.gymtracker.workoutapi.common.sort.application.EntitySorter;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteSetGroupServiceImpl implements DeleteSetGroupService {

  private final SetGroupFacade setGroupFacade;
  private final ExercisesFacade exercisesFacade;
  private final AuthWorkoutsService authWorkoutsService;
  private final EntitySorter entitySorter;

  @Override
  @Transactional
  public void execute(final UUID setGroupId)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    final SetGroup setGroup = setGroupFacade.getSetGroupWithWorkout(setGroupId);

    authWorkoutsService.checkAccess(setGroup, AuthOperations.DELETE);

    try {
      exercisesFacade.decrementExerciseUses(
          setGroup.getWorkout().getUserId(), setGroup.getExerciseId());
    } catch (ExerciseUnavailableException ex) {
      log.error("Error decrementing exercise usage", ex);
    }

    final List<SetGroup> setGroups =
        setGroupFacade.getSetGroupsByWorkoutId(setGroup.getWorkout().getId());

    setGroupFacade.deleteSetGroup(setGroupId);

    if (!isSetGroupDeletedLastSetGroup(setGroup, setGroups)) {
      reorderWorkoutSetGroups(setGroups, setGroup);
    }
  }

  private void reorderWorkoutSetGroups(final List<SetGroup> setGroups, final SetGroup setGroup) {
    entitySorter.sortDelete(setGroups, setGroup);
    setGroupFacade.saveSetGroups(setGroups);
  }

  private boolean isSetGroupDeletedLastSetGroup(
      final SetGroup setGroup, final List<SetGroup> setGroups) {
    return setGroup.getListOrder() == setGroups.size() - 1;
  }
}
