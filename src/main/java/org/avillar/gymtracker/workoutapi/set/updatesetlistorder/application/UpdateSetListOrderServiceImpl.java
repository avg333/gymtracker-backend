package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application;

import jakarta.transaction.Transactional;
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
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.mapper.UpdateSetListOrderServiceMapper;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.model.UpdateSetListOrderResponseApplication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateSetListOrderServiceImpl implements UpdateSetListOrderService {

  private final SetDao setDao;
  private final EntitySorter entitySorter;
  private final AuthWorkoutsService authWorkoutsService;
  private final UpdateSetListOrderServiceMapper updateSetListOrderServiceMapper;

  @Override
  @Transactional
  public List<UpdateSetListOrderResponseApplication> execute(final UUID setId, final int listOrder)
      throws EntityNotFoundException, IllegalAccessException {
    final Set set = getSetFull(setId);

    authWorkoutsService.checkAccess(set, AuthOperations.UPDATE);

    final List<Set> sets = setDao.getSetsBySetGroupId(set.getSetGroup().getId());

    final int oldPosition = set.getListOrder();
    final int newPosition = EntitySorter.getValidListOrder(listOrder, sets.size());

    if (oldPosition == newPosition) {
      return updateSetListOrderServiceMapper.map(sets);
    }

    sets.stream()
        .filter(s -> s.getId().equals(setId))
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Set.class, setId))
        .setListOrder(newPosition);

    entitySorter.sortUpdate(sets, set, oldPosition);
    setDao.saveAll(sets); // FIXME Devolver todas las sets

    return updateSetListOrderServiceMapper.map(sets);
  }

  private Set getSetFull(final UUID setId) {
    return setDao.getSetFullById(setId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Set.class, setId));
  }
}
