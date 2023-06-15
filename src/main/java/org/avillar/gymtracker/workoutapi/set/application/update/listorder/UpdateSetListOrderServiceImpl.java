package org.avillar.gymtracker.workoutapi.set.application.update.listorder;

import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.sort.application.EntitySorter;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.set.application.update.listorder.mapper.UpdateSetListOrderServiceMapper;
import org.avillar.gymtracker.workoutapi.set.application.update.listorder.model.UpdateSetListOrderResponse;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.avillar.gymtracker.workoutapi.set.domain.SetDao;
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
  public UpdateSetListOrderResponse updateSetListOrder(final UUID setId, final int listOrder) {
    final Set set = getSetFull(setId);

    authWorkoutsService.checkAccess(set, AuthOperations.UPDATE);

    final java.util.Set<Set> sets = setDao.getSetsBySetGroupId(set.getSetGroup().getId());

    final int oldPosition = set.getListOrder();
    final int newPosition = EntitySorter.getValidListOrder(listOrder, sets.size());

    if (oldPosition == newPosition) {
      return new UpdateSetListOrderResponse(updateSetListOrderServiceMapper.updateResponse(sets));
    }

    sets.stream()
        .filter(s -> s.getId().equals(setId))
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Set.class, setId))
        .setListOrder(newPosition);

    entitySorter.sortUpdate(sets, set, oldPosition);
    setDao.saveAll(sets);

    return new UpdateSetListOrderResponse(updateSetListOrderServiceMapper.updateResponse(sets));
  }

  private Set getSetFull(final UUID setId) {
    return setDao.getSetFullById(setId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Set.class, setId));
  }
}
