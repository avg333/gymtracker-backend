package org.avillar.gymtracker.workoutapi.workout.copysetgroups.application;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.common.facade.set.SetFacade;
import org.avillar.gymtracker.workoutapi.common.facade.setgroup.SetGroupFacade;
import org.avillar.gymtracker.workoutapi.common.facade.workout.WorkoutFacade;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.CopySetGroupsServiceImpl.CopySetGroupsRequest.Source;
import org.springframework.stereotype.Service;

// TODO Finish this
@Service
@RequiredArgsConstructor
public class CopySetGroupsServiceImpl implements CopySetGroupsService {

  private final WorkoutFacade workoutFacade;
  private final SetGroupFacade setGroupFacade;
  private final SetFacade setFacade;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  @Transactional
  public List<SetGroup> execute(
      final UUID workoutDestinationId, final CopySetGroupsRequest copySetGroupsRequest)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {

    if (copySetGroupsRequest.getSource().equals(Source.SESSION)) {
      throw new NotImplementedException();
    }

    final List<Workout> workouts =
        workoutFacade.getFullWorkoutsByIds(
            List.of(workoutDestinationId, copySetGroupsRequest.getId()));
    final Workout workoutSource =
        getWorkoutByIdFromCollection(workouts, copySetGroupsRequest.getId());
    final Workout workoutDestination = getWorkoutByIdFromCollection(workouts, workoutDestinationId);

    authWorkoutsService.checkAccess(workoutDestination, AuthOperations.UPDATE);
    authWorkoutsService.checkAccess(workoutSource, AuthOperations.READ);

    return copySetGroupsToWorkout(workoutDestination, workoutSource.getSetGroups());
  }

  private List<SetGroup> copySetGroupsToWorkout(
      final Workout workout, final Collection<SetGroup> setGroupsSource) {
    final int listOrderOffset = workout.getSetGroups().size();

    final List<SetGroup> setGroups = new ArrayList<>(setGroupsSource.size());
    final List<Set> sets = new ArrayList<>();
    for (final SetGroup setGroupDb : setGroupsSource) {
      final SetGroup setGroup = setGroupDb.createCopy();
      setGroup.setListOrder(setGroupDb.getListOrder() + listOrderOffset);
      setGroup.setWorkout(workout);
      setGroups.add(setGroup);
      for (final Set setDb : setGroupDb.getSets()) {
        final Set set = setDb.createCopy();
        set.setSetGroup(setGroup);
        sets.add(set);
      }
    }

    setGroupFacade.saveSetGroups(setGroups);
    setFacade.saveSets(sets);

    return Stream.concat(workout.getSetGroups().stream(), setGroups.stream()).toList();
  }

  private Workout getWorkoutByIdFromCollection(
      final Collection<Workout> workouts, final UUID workoutId) throws WorkoutNotFoundException {
    return workouts.stream()
        .filter(workout -> Objects.equals(workout.getId(), workoutId))
        .findAny()
        .orElseThrow(() -> new WorkoutNotFoundException(workoutId));
  }

  @Getter
  @Builder
  public static class CopySetGroupsRequest {

    private UUID id;

    private Source source;

    public enum Source {
      WORKOUT,
      SESSION
    }
  }
}
