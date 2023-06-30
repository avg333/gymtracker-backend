package org.avillar.gymtracker.workoutapi.set.deleteset.application;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.common.sort.application.EntitySorter;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteSetServiceImpl implements DeleteSetService {

  private final SetDao setDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final EntitySorter entitySorter;

  @Override
  @Transactional
  public void execute(final UUID setId) throws EntityNotFoundException, IllegalAccessException {
    final Set set = getSetFull(setId);

    authWorkoutsService.checkAccess(set, AuthOperations.DELETE);

    setDao.deleteById(setId);

    final List<Set> sets = setDao.getSetsBySetGroupId(set.getSetGroup().getId());

    if (set.getListOrder() != sets.size() - 1) {
      reorderSets(sets, set);
    }
  }

  private void reorderSets(final List<Set> sets, final Set set) {
    entitySorter.sortDelete(sets, set);
    setDao.saveAll(sets);
  }

  private Set getSetFull(final UUID setId) {
    return setDao.getSetFullById(setId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Set.class, setId));
  }
}
