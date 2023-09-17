package org.avillar.gymtracker.workoutapi.setgroup.deletesetgroup.application;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.common.sort.application.EntitySorter;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.exercise.application.facade.ExerciseRepositoryClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteSetGroupServiceImpl implements DeleteSetGroupService {

  private final SetGroupDao setGroupDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final EntitySorter entitySorter;
  private final ExerciseRepositoryClient exerciseRepositoryClient;

  @Override
  @Transactional
  public void execute(final UUID setGroupId)
      throws EntityNotFoundException, IllegalAccessException {
    final SetGroup setGroup = getSetGroupWithWorkout(setGroupId);

    authWorkoutsService.checkAccess(setGroup, AuthOperations.DELETE);

    exerciseRepositoryClient.decrementExerciseUses(setGroup.getExerciseId());

    setGroupDao.deleteById(setGroupId);

    final List<SetGroup> setGroups =
        setGroupDao.getSetGroupsByWorkoutId(setGroup.getWorkout().getId());

    if (setGroup.getListOrder() != setGroups.size() - 1) {
      reorderSetGroups(setGroups, setGroup);
    }
  }

  private void reorderSetGroups(final List<SetGroup> setGroups, final SetGroup setGroup) {
    entitySorter.sortDelete(setGroups, setGroup);
    setGroupDao.saveAll(setGroups);
  }

  private SetGroup getSetGroupWithWorkout(final UUID setGroupId) {
    return setGroupDao.getSetGroupWithWorkoutById(setGroupId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId));
  }
}
