package org.avillar.gymtracker.workoutapi.workout.application.update.setgroups;

import jakarta.transaction.Transactional;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.avillar.gymtracker.workoutapi.set.domain.SetDao;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.workout.application.update.setgroups.mapper.UpdateWorkoutSetGroupsServiceMapper;
import org.avillar.gymtracker.workoutapi.workout.application.update.setgroups.model.UpdateWorkoutSetGroupsResponse;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.domain.WorkoutDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateWorkoutSetGroupsServiceImpl implements UpdateWorkoutSetGroupsService {

  private final WorkoutDao workoutDao;
  private final SetGroupDao setGroupDao;
  private final SetDao setDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final UpdateWorkoutSetGroupsServiceMapper updateWorkoutSetGroupsServiceMapper;

  @Override
  @Transactional
  public UpdateWorkoutSetGroupsResponse addSetGroupsToWorkoutFromWorkout(
      final UUID workoutDestinationId, final UUID workoutSourceId) {

    final java.util.Set<Workout> workouts = // TODO Es necesario devolver las sets?
        workoutDao.getFullWorkoutByIds(List.of(workoutDestinationId, workoutSourceId));
    final Workout workoutSource = getWorkoutByIdFromCollection(workouts, workoutSourceId);
    final Workout workoutDestination = getWorkoutByIdFromCollection(workouts, workoutDestinationId);

    authWorkoutsService.checkAccess(workoutDestination, AuthOperations.UPDATE);
    authWorkoutsService.checkAccess(workoutSource, AuthOperations.READ);

    return new UpdateWorkoutSetGroupsResponse(
        updateWorkoutSetGroupsServiceMapper.updateResponse(
            copySetGroupsToWorkout(
                workoutDestination,
                workoutSource.getSetGroups()))); // TODO Es correcta esta respuesta a devolver?
  }

  @Override
  public UpdateWorkoutSetGroupsResponse addSetGroupsToWorkoutFromSession(
      final UUID workoutDestinationId, final UUID sessionSourceId) {
    throw new NotImplementedException();
  }

  private List<SetGroup> copySetGroupsToWorkout(
      final Workout workout, final java.util.Set<SetGroup> setGroupsSource) {
    final int listOrderOffset = workout.getSetGroups().size();

    // TODO Cambiar esto a lambda
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