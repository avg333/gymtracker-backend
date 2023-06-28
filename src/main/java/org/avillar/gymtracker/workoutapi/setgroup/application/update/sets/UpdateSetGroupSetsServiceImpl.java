package org.avillar.gymtracker.workoutapi.setgroup.application.update.sets;

import jakarta.transaction.Transactional;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetDao;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.sets.mapper.UpdateSetGroupSetsServiceMapper;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.sets.model.UpdateSetGroupSetsResponseApplication;
import org.springframework.stereotype.Service;

// FINALIZAR
@Service
@RequiredArgsConstructor
public class UpdateSetGroupSetsServiceImpl implements UpdateSetGroupSetsService {

  private final SetGroupDao setGroupDao;
  private final SetDao setDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final UpdateSetGroupSetsServiceMapper updateSetGroupSetsServiceMapper;

  @Override
  @Transactional
  public UpdateSetGroupSetsResponseApplication execute(
      final UUID setGroupDestinationId, final UUID setGroupSourceId) {
    final List<SetGroup> setGroups =
        setGroupDao.getSetGroupFullByIds(List.of(setGroupDestinationId, setGroupSourceId));
    final SetGroup setGroupSource = getSetGroupByIdFromCollection(setGroups, setGroupSourceId);
    final SetGroup setGroupDestination =
        getSetGroupByIdFromCollection(setGroups, setGroupDestinationId);

    authWorkoutsService.checkAccess(setGroupSource, AuthOperations.DELETE);
    authWorkoutsService.checkAccess(setGroupDestination, AuthOperations.UPDATE);

    final List<Set> sets = new ArrayList<>(setGroupSource.getSets().size());
    for (final Set setDb : setGroupSource.getSets()) {
      final Set set =
          Set.clone(setDb);
      set.setSetGroup(setGroupDestination);
      sets.add(set);
    }

    setGroupDao.deleteById(setGroupSourceId); // TODO Es correcto borrar el source?
    setDao.saveAll(sets);

    return new UpdateSetGroupSetsResponseApplication(updateSetGroupSetsServiceMapper.map(sets));
  }

  private SetGroup getSetGroupByIdFromCollection(
      final Collection<SetGroup> setGroups, final UUID setGroupId) {
    return setGroups.stream()
        .filter(setGroup -> Objects.equals(setGroup.getId(), setGroupId))
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId));
  }
}
