package org.avillar.gymtracker.workoutapi.workout.copysetgroups.application;

import jakarta.transaction.Transactional;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetDao;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.setgroup.mapper.GetSetGroupServiceMapper;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.mapper.UpdateWorkoutSetGroupsServiceMapper;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.model.UpdateWorkoutSetGroupsResponseApplication;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.springframework.stereotype.Service;

// FINALIZAR
@Service
@RequiredArgsConstructor
public class UpdateWorkoutSetGroupsServiceImpl implements UpdateWorkoutSetGroupsService {

  private final WorkoutDao workoutDao;
  private final SetGroupDao setGroupDao;
  private final SetDao setDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final UpdateWorkoutSetGroupsServiceMapper updateWorkoutSetGroupsServiceMapper;
  private final GetSetGroupServiceMapper getSetGroupServiceMapper;

  @Override
  @Transactional
  public UpdateWorkoutSetGroupsResponseApplication addSetGroupsToWorkoutFromWorkout(
      final UUID workoutDestinationId, final UUID workoutSourceId) {

    final java.util.Set<Workout> workouts = // TODO Es necesario devolver las sets?
        workoutDao.getFullWorkoutByIds(List.of(workoutDestinationId, workoutSourceId));
    final Workout workoutSource = getWorkoutByIdFromCollection(workouts, workoutSourceId);
    final Workout workoutDestination = getWorkoutByIdFromCollection(workouts, workoutDestinationId);

    authWorkoutsService.checkAccess(workoutDestination, AuthOperations.UPDATE);
    authWorkoutsService.checkAccess(workoutSource, AuthOperations.READ);

    return new UpdateWorkoutSetGroupsResponseApplication(
        updateWorkoutSetGroupsServiceMapper.map(
            copySetGroupsToWorkout(
                workoutDestination,
                workoutSource.getSetGroups()))); // TODO Es correcta esta respuesta a devolver?
  }

  @Override
  public UpdateWorkoutSetGroupsResponseApplication addSetGroupsToWorkoutFromSession(
      final UUID workoutDestinationId, final UUID sessionSourceId) {
    throw new NotImplementedException();
  }

  private List<SetGroup> copySetGroupsToWorkout(
      final Workout workout, final java.util.Set<SetGroup> setGroupsSource) {
    final int listOrderOffset = workout.getSetGroups().size();

    final List<SetGroup> setGroups = new ArrayList<>(setGroupsSource.size());
    final List<Set> sets = new ArrayList<>();
    for (final SetGroup setGroupDb : setGroupsSource) {
      final SetGroup setGroup = SetGroup.clone(setGroupDb);
      setGroup.setListOrder(setGroupDb.getListOrder() + listOrderOffset);
      setGroup.setWorkout(workout);
      setGroups.add(setGroup);
      for (final Set setDb : setGroupDb.getSets()) {
        final Set set = Set.clone(setDb);
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
