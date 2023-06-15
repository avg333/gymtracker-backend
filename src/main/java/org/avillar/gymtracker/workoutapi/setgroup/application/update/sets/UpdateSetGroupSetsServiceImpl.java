package org.avillar.gymtracker.workoutapi.setgroup.application.update.sets;

import jakarta.transaction.Transactional;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.avillar.gymtracker.workoutapi.set.domain.SetDao;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.sets.mapper.UpdateSetGroupSetsServiceMapper;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.sets.model.UpdateSetGroupSetsResponse;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroupDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateSetGroupSetsServiceImpl implements UpdateSetGroupSetsService {

  private final SetGroupDao setGroupDao;
  private final SetDao setDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final UpdateSetGroupSetsServiceMapper updateSetGroupSetsServiceMapper;

  @Override
  @Transactional
  public UpdateSetGroupSetsResponse update(
      final UUID setGroupDestinationId, final UUID setGroupSourceId) {
    final List<SetGroup> setGroups =
        setGroupDao.getSetGroupFullByIds(List.of(setGroupDestinationId, setGroupSourceId));
    final SetGroup setGroupSource = getSetGroupByIdFromCollection(setGroups, setGroupSourceId);
    final SetGroup setGroupDestination =
        getSetGroupByIdFromCollection(setGroups, setGroupDestinationId);

    authWorkoutsService.checkAccess(setGroupSource, AuthOperations.DELETE);
    authWorkoutsService.checkAccess(setGroupDestination, AuthOperations.UPDATE);

    final List<Set> sets = new ArrayList<>(setGroupSource.getSets().size());
    for (final org.avillar.gymtracker.workoutapi.set.domain.Set setDb : setGroupSource.getSets()) {
      final org.avillar.gymtracker.workoutapi.set.domain.Set set =
          org.avillar.gymtracker.workoutapi.set.domain.Set.clone(setDb);
      set.setSetGroup(setGroupDestination);
      sets.add(set);
    }

    setGroupDao.deleteById(setGroupSourceId); // TODO Es correcto borrar el source?
    setDao.saveAll(sets);

    return new UpdateSetGroupSetsResponse(updateSetGroupSetsServiceMapper.updateResponse(sets));
  }

  private SetGroup getSetGroupByIdFromCollection(
      final Collection<SetGroup> setGroups, final UUID setGroupId) {
    return setGroups.stream()
        .filter(setGroup -> Objects.equals(setGroup.getId(), setGroupId))
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId));
  }
}
