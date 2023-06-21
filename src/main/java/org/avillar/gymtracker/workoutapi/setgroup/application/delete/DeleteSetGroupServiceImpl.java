package org.avillar.gymtracker.workoutapi.setgroup.application.delete;

import jakarta.transaction.Transactional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.sort.application.EntitySorter;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroupDao;
import org.springframework.stereotype.Service;

// RDY
@Service
@RequiredArgsConstructor
public class DeleteSetGroupServiceImpl implements DeleteSetGroupService {

  private final SetGroupDao setGroupDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final EntitySorter entitySorter;

  @Override
  @Transactional
  public void delete(final UUID setGroupId) {
    final SetGroup setGroup = getSetGroupWithWorkout(setGroupId);

    authWorkoutsService.checkAccess(setGroup, AuthOperations.DELETE);

    setGroupDao.deleteById(setGroupId);

    final Set<SetGroup> setGroups =
        setGroupDao.getSetGroupsByWorkoutId(setGroup.getWorkout().getId());

    if (setGroup.getListOrder() != setGroups.size() - 1) {
      reorderSetGroups(setGroups, setGroup);
    }
  }

  private void reorderSetGroups(final Set<SetGroup> setGroups, final SetGroup setGroup) {
    entitySorter.sortDelete(setGroups, setGroup);
    setGroupDao.saveAll(setGroups);
  }

  private SetGroup getSetGroupWithWorkout(final UUID setGroupId) {
    return setGroupDao.getSetGroupWithWorkoutById(setGroupId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId));
  }
}
