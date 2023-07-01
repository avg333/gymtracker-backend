package org.avillar.gymtracker.workoutapi.workout.copysetgroups.application;

import jakarta.transaction.Transactional;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.SetDao;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.mapper.CopySetGroupsServiceMapper;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.model.CopySetGroupsResponseApplication;
import org.springframework.stereotype.Service;

// FINALIZAR
@Service
@RequiredArgsConstructor
public class CopySetGroupsServiceImpl implements CopySetGroupsService {

  private final WorkoutDao workoutDao;
  private final SetGroupDao setGroupDao;
  private final SetDao setDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final CopySetGroupsServiceMapper copySetGroupsServiceMapper;

  @Override
  @Transactional
  public List<CopySetGroupsResponseApplication> execute(
      final UUID workoutDestinationId, final UUID workoutSourceId, final boolean sourceWorkout)
      throws EntityNotFoundException, IllegalAccessException {

    if (!sourceWorkout) {
      throw new NotImplementedException();
    }

    final java.util.Set<Workout> workouts =
        workoutDao.getFullWorkoutByIds(List.of(workoutDestinationId, workoutSourceId));
    final Workout workoutSource = getWorkoutByIdFromCollection(workouts, workoutSourceId);
    final Workout workoutDestination = getWorkoutByIdFromCollection(workouts, workoutDestinationId);

    authWorkoutsService.checkAccess(workoutDestination, AuthOperations.UPDATE);
    authWorkoutsService.checkAccess(workoutSource, AuthOperations.READ);

    return copySetGroupsServiceMapper.map(
        copySetGroupsToWorkout(workoutDestination, workoutSource.getSetGroups()));
  }

  private List<SetGroup> copySetGroupsToWorkout(
      final Workout workout, final java.util.Set<SetGroup> setGroupsSource) {
    final int listOrderOffset = workout.getSetGroups().size();

    final List<SetGroup> setGroups = new ArrayList<>(setGroupsSource.size());
    final List<org.avillar.gymtracker.workoutapi.domain.Set> sets = new ArrayList<>();
    for (final SetGroup setGroupDb : setGroupsSource) {
      final SetGroup setGroup = SetGroup.clone(setGroupDb);
      setGroup.setListOrder(setGroupDb.getListOrder() + listOrderOffset);
      setGroup.setWorkout(workout);
      setGroups.add(setGroup);
      for (final org.avillar.gymtracker.workoutapi.domain.Set setDb : setGroupDb.getSets()) {
        final org.avillar.gymtracker.workoutapi.domain.Set set = org.avillar.gymtracker.workoutapi.domain.Set.clone(setDb);
        set.setSetGroup(setGroup);
        sets.add(set);
      }
    }

    setGroupDao.saveAll(setGroups);
    setDao.saveAll(sets);

    final List<SetGroup> totalSetGroups = new ArrayList<>(workout.getSetGroups());
    totalSetGroups.addAll(setGroups);

    return totalSetGroups;
  }

  private Workout getWorkoutByIdFromCollection(
      final Collection<Workout> workouts, final UUID workoutId) {
    return workouts.stream()
        .filter(workout -> Objects.equals(workout.getId(), workoutId))
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Workout.class, workoutId));
  }
}
