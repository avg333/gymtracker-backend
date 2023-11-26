package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.exception.application.ListOrderNotValidException;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.facade.set.SetFacade;
import org.avillar.gymtracker.workoutapi.common.sort.application.EntitySorter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateSetListOrderServiceImpl implements UpdateSetListOrderService {

  private final SetFacade setFacade;
  private final EntitySorter entitySorter;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  @Transactional
  public List<Set> execute(final UUID setId, final int listOrder)
      throws SetNotFoundException, WorkoutIllegalAccessException, ListOrderNotValidException {
    final Set set = setFacade.getSetFull(setId);

    authWorkoutsService.checkAccess(set, AuthOperations.UPDATE);

    final List<Set> sets = setFacade.getSetsBySetGroupId(set.getSetGroup().getId());

    final int oldPosition = set.getListOrder();
    final int newPosition = EntitySorter.getValidListOrder(listOrder, sets.size());
    // TODO Change with validator

    if (oldPosition == newPosition) {
      return sets;
    }

    set.setListOrder(newPosition);

    entitySorter.sortUpdate(sets, set);

    return setFacade.saveSets(sets);
    // FIXME Must return all the sets
  }
}
