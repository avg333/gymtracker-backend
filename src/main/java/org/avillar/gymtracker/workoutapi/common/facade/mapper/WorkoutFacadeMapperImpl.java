package org.avillar.gymtracker.workoutapi.common.facade.mapper;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.model.SetEntity;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.model.SetGroupEntity;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.model.WorkoutEntity;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.springframework.stereotype.Component;

@Component
public class WorkoutFacadeMapperImpl implements WorkoutFacadeMapper {

  @Override
  public List<SetGroupEntity> mapSetGroups(final List<SetGroup> setGroups) {

    if (setGroups == null) {
      return null;
    }

    return setGroups.stream().map(this::map).toList();
  }

  @Override
  public List<SetEntity> mapSets(final List<Set> sets) {

    if (sets == null) {
      return null;
    }

    return sets.stream().map(this::map).toList();
  }

  @Override
  public WorkoutEntity map(final Workout workout) {

    if (workout == null) {
      return null;
    }

    final WorkoutEntity workoutEntity = new WorkoutEntity();
    workoutEntity.setId(workout.getId());
    workoutEntity.setDate(Date.valueOf(workout.getDate()));
    workoutEntity.setDescription(workout.getDescription());
    workoutEntity.setUserId(workout.getUserId());
    return workoutEntity;
  }

  @Override
  public SetGroupEntity map(final SetGroup setGroup) {

    if (setGroup == null) {
      return null;
    }

    final SetGroupEntity setGroupEntity = new SetGroupEntity();
    setGroupEntity.setId(setGroup.getId());
    setGroupEntity.setListOrder(setGroup.getListOrder());
    setGroupEntity.setDescription(setGroup.getDescription());
    setGroupEntity.setExerciseId(setGroup.getExerciseId());
    setGroupEntity.setWorkout(
        Optional.ofNullable(setGroup.getWorkout())
            .map(workout -> new WorkoutEntity().withId(workout.getId()))
            .orElse(null));
    setGroupEntity.setRest(setGroup.getRest());
    setGroupEntity.setEccentric(setGroup.getEccentric());
    setGroupEntity.setFirstPause(setGroup.getFirstPause());
    setGroupEntity.setConcentric(setGroup.getConcentric());
    setGroupEntity.setSecondPause(setGroup.getSecondPause());
    setGroupEntity.setSuperSetWithNext(setGroup.getSuperSetWithNext());

    return setGroupEntity;
  }

  @Override
  public SetEntity map(final Set set) {

    if (set == null) {
      return null;
    }

    final SetEntity setEntity = new SetEntity();
    setEntity.setId(set.getId());
    setEntity.setListOrder(set.getListOrder());
    setEntity.setDescription(set.getDescription());
    setEntity.setReps(set.getReps());
    setEntity.setRir(set.getRir());
    setEntity.setWeight(set.getWeight());
    setEntity.setCompletedAt(
        Optional.ofNullable(set.getCompletedAt())
            .map(date -> new Timestamp(date.getTime()))
            .orElse(null));
    setEntity.setSetGroup(
        Optional.ofNullable(set.getSetGroup())
            .map(setGroup -> new SetGroupEntity().withId(setGroup.getId()))
            .orElse(null));

    return setEntity;
  }
}
