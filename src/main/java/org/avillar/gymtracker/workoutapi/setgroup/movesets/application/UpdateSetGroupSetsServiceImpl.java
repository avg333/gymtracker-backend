package org.avillar.gymtracker.workoutapi.setgroup.movesets.application;

import jakarta.transaction.Transactional;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.facade.set.SetFacade;
import org.avillar.gymtracker.workoutapi.common.facade.setgroup.SetGroupFacade;
import org.springframework.stereotype.Service;

// TODO Finish this
@Service
@RequiredArgsConstructor
public class UpdateSetGroupSetsServiceImpl implements UpdateSetGroupSetsService {

  private final SetGroupFacade setGroupFacade;
  private final SetFacade setFacade;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  @Transactional
  public List<Set> execute(final UUID setGroupDestinationId, final UUID setGroupSourceId)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    final List<SetGroup> setGroups =
        setGroupFacade.getSetGroupFullByIds(List.of(setGroupDestinationId, setGroupSourceId));
    final SetGroup setGroupSource = getSetGroupByIdFromCollection(setGroups, setGroupSourceId);
    final SetGroup setGroupDestination =
        getSetGroupByIdFromCollection(setGroups, setGroupDestinationId);

    authWorkoutsService.checkAccess(setGroupSource, AuthOperations.DELETE);
    authWorkoutsService.checkAccess(setGroupDestination, AuthOperations.UPDATE);

    final List<Set> sets = new ArrayList<>(setGroupSource.getSets().size());
    for (final Set setDb : setGroupSource.getSets()) {
      final Set set = setDb.createCopy();
      set.setSetGroup(setGroupDestination);
      sets.add(set);
    }

    setGroupFacade.deleteSetGroup(setGroupSourceId);
    setFacade.saveSets(sets);

    return sets;
  }

  private SetGroup getSetGroupByIdFromCollection(
      final Collection<SetGroup> setGroups, final UUID setGroupId)
      throws SetGroupNotFoundException {
    return setGroups.stream()
        .filter(setGroup -> setGroup.getId().equals(setGroupId))
        .findAny()
        .orElseThrow(() -> new SetGroupNotFoundException(setGroupId));
  }
}
