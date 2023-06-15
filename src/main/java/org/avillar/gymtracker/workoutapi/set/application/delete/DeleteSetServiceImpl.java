package org.avillar.gymtracker.workoutapi.set.application.delete;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.sort.application.EntitySorter;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.avillar.gymtracker.workoutapi.set.domain.SetDao;
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
  public void delete(final UUID setId) {
    final Set set = getSetFull(setId);

    authWorkoutsService.checkAccess(set, AuthOperations.DELETE);

    setDao.deleteById(setId);

    final java.util.Set<Set> sets = setDao.getSetsBySetGroupId(set.getSetGroup().getId());

    if (set.getListOrder() != sets.size() - 1) {
      reorderSets(sets, set);
    }
  }

  private void reorderSets(final java.util.Set<Set> sets, final Set set) {
    entitySorter.sortDelete(sets, set);
    setDao.saveAll(sets);
  }

  private Set getSetFull(final UUID setId) {
    return setDao.getSetFullById(setId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Set.class, setId));
  }
}
