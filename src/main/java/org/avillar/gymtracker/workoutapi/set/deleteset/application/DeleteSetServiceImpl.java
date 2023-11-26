package org.avillar.gymtracker.workoutapi.set.deleteset.application;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.facade.set.SetFacade;
import org.avillar.gymtracker.workoutapi.common.sort.application.EntitySorter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteSetServiceImpl implements DeleteSetService {

  private final SetFacade setFacade;
  private final AuthWorkoutsService authWorkoutsService;
  private final EntitySorter entitySorter;

  @Override
  @Transactional
  public void execute(final UUID setId) throws SetNotFoundException, WorkoutIllegalAccessException {
    final Set set = setFacade.getSetFull(setId);

    authWorkoutsService.checkAccess(set, AuthOperations.DELETE);

    final List<Set> sets = setFacade.getSetsBySetGroupId(set.getSetGroup().getId());

    setFacade.deleteSet(setId);

    if (!isSetDeletedLastSet(set, sets)) {
      reorderSetGroupSets(sets, set);
    }
  }

  private void reorderSetGroupSets(final List<Set> sets, final Set set) {
    entitySorter.sortDelete(sets, set);
    setFacade.saveSets(sets);
  }

  private boolean isSetDeletedLastSet(final Set set, final List<Set> sets) {
    return set.getListOrder() == sets.size() - 1;
  }
}
