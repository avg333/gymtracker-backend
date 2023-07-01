package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.common.sort.application.EntitySorter;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application.model.UpdateSetGroupListOrderResponseApplication;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application.mapper.UpdateSetGroupListOrderServiceMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateSetGroupListOrderServiceImpl implements UpdateSetGroupListOrderService {

  private final SetGroupDao setGroupDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final EntitySorter entitySorter;
  private final UpdateSetGroupListOrderServiceMapper updateSetGroupListOrderServiceMapper;

  @Override
  public List<UpdateSetGroupListOrderResponseApplication> execute(
      final UUID setGroupId, final int listOrder)
      throws EntityNotFoundException, IllegalAccessException {
    final SetGroup setGroup = getSetGroupWithWorkout(setGroupId);

    authWorkoutsService.checkAccess(setGroup, AuthOperations.UPDATE);

    final Set<SetGroup> setGroups =
        setGroupDao.getSetGroupsByWorkoutId(setGroup.getWorkout().getId());

    final int oldPosition = setGroup.getListOrder();
    final int newPosition = EntitySorter.getValidListOrder(listOrder, setGroups.size());

    if (oldPosition == newPosition) {
      return updateSetGroupListOrderServiceMapper.map(setGroups);
    }

    setGroups.stream()
        .filter(sg -> sg.getId().equals(setGroupId))
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId))
        .setListOrder(newPosition);

    entitySorter.sortUpdate(setGroups, setGroup, oldPosition);
    setGroupDao.saveAll(setGroups);

    return updateSetGroupListOrderServiceMapper.map(setGroups);
  }

  private SetGroup getSetGroupWithWorkout(final UUID setGroupId) {
    return setGroupDao.getSetGroupWithWorkoutById(setGroupId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId));
  }
}
