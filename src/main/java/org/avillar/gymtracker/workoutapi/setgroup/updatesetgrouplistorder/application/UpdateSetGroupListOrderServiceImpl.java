package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.ListOrderNotValidException;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.facade.setgroup.SetGroupFacade;
import org.avillar.gymtracker.workoutapi.common.sort.application.EntitySorter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateSetGroupListOrderServiceImpl implements UpdateSetGroupListOrderService {

  private final SetGroupFacade setGroupFacade;
  private final AuthWorkoutsService authWorkoutsService;
  private final EntitySorter entitySorter;

  @Override
  public List<SetGroup> execute(final UUID setGroupId, final int listOrder)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException, ListOrderNotValidException {
    final SetGroup setGroup = setGroupFacade.getSetGroupWithWorkout(setGroupId);

    authWorkoutsService.checkAccess(setGroup, AuthOperations.UPDATE);

    final List<SetGroup> setGroups =
        setGroupFacade.getSetGroupsByWorkoutId(setGroup.getWorkout().getId());

    final int oldPosition = setGroup.getListOrder();
    final int newPosition = EntitySorter.getValidListOrder(listOrder, setGroups.size());
    // TODO Change to validator

    if (oldPosition == newPosition) {
      return setGroups;
    }

    setGroup.setListOrder(newPosition);

    entitySorter.sortUpdate(setGroups, setGroup);

    return setGroupFacade.saveSetGroups(setGroups);
    // FIXME Must return all the sgs
  }
}
