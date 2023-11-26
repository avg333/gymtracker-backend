package org.avillar.gymtracker.workoutapi.common.facade.mapper;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.model.SetEntity;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.model.SetGroupEntity;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.model.WorkoutEntity;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

// TODO Fix circular mapping
@Component
public class WorkoutEntityFacadeMapperImpl implements WorkoutEntityFacadeMapper {

  @Override
  public Map<LocalDate, UUID> mapMap(final Map<Date, UUID> workoutsIdAndDate) {

    if (workoutsIdAndDate == null || !Hibernate.isInitialized(workoutsIdAndDate)) {
      return Collections.emptyMap();
    }

    return workoutsIdAndDate.entrySet().stream()
        .collect(Collectors.toMap(entry -> entry.getKey().toLocalDate(), Map.Entry::getValue));
  }

  @Override
  public List<Workout> mapWorkouts(final List<WorkoutEntity> workoutEntity) {

    if (workoutEntity == null || !Hibernate.isInitialized(workoutEntity)) {
      return Collections.emptyList();
    }

    return workoutEntity.stream().map(this::map).collect(Collectors.toList());
  }

  @Override
  public List<SetGroup> mapSetGroups(final Collection<SetGroupEntity> setGroupEntities) {

    if (setGroupEntities == null || !Hibernate.isInitialized(setGroupEntities)) {
      return Collections.emptyList();
    }

    return setGroupEntities.stream()
        .map(setGroupEntity -> map(setGroupEntity, false))
        .collect(Collectors.toList());
  }

  @Override
  public List<Set> mapSets(final Collection<SetEntity> setEntities) {

    if (setEntities == null || !Hibernate.isInitialized(setEntities)) {
      return Collections.emptyList();
    }

    return setEntities.stream()
        .map(setEntity -> map(setEntity, false))
        .collect(Collectors.toList());
  }

  @Override
  public Workout map(final WorkoutEntity workoutEntity) {

    if (workoutEntity == null || !Hibernate.isInitialized(workoutEntity)) {
      return null;
    }

    return Workout.builder()
        .id(workoutEntity.getId())
        .date(Optional.ofNullable(workoutEntity.getDate()).map(Date::toLocalDate).orElse(null))
        .description(workoutEntity.getDescription())
        .userId(workoutEntity.getUserId())
        .setGroups(mapSetGroups(workoutEntity.getSetGroups()))
        .build();
  }

  @Override
  public SetGroup map(final SetGroupEntity setGroupEntity) {

    return map(setGroupEntity, true);
  }

  private SetGroup map(final SetGroupEntity setGroupEntity, final boolean mapParent) {

    if (setGroupEntity == null || !Hibernate.isInitialized(setGroupEntity)) {
      return null;
    }

    final SetGroup setGroup =
        SetGroup.builder()
            .id(setGroupEntity.getId())
            .listOrder(setGroupEntity.getListOrder())
            .description(setGroupEntity.getDescription())
            .exerciseId(setGroupEntity.getExerciseId())
            .sets(mapSets(setGroupEntity.getSets()))
            .rest(setGroupEntity.getRest())
            .eccentric(setGroupEntity.getEccentric())
            .firstPause(setGroupEntity.getFirstPause())
            .concentric(setGroupEntity.getConcentric())
            .secondPause(setGroupEntity.getSecondPause())
            .superSetWithNext(setGroupEntity.getSuperSetWithNext())
            .build();

    final WorkoutEntity parent = setGroupEntity.getWorkout();

    if (parent != null && Hibernate.isInitialized(parent)) {
      if (mapParent) {
        setGroup.setWorkout(map(parent));
      } else {
        setGroup.setWorkout(Workout.builder().id(parent.getId()).build());
      }
    }

    return setGroup;
  }

  @Override
  public Set map(final SetEntity setEntity) {

    return map(setEntity, true);
  }

  private Set map(final SetEntity setEntity, final boolean mapParent) {

    if (setEntity == null || !Hibernate.isInitialized(setEntity)) {
      return null;
    }

    final Set set =
        Set.builder()
            .id(setEntity.getId())
            .listOrder(setEntity.getListOrder())
            .description(setEntity.getDescription())
            .reps(setEntity.getReps())
            .rir(setEntity.getRir())
            .weight(setEntity.getWeight())
            .completedAt(setEntity.getCompletedAt())
            .build();

    final SetGroupEntity parent = setEntity.getSetGroup();

    if (parent != null && Hibernate.isInitialized(parent)) {
      if (mapParent) {
        set.setSetGroup(map(parent));
      } else {
        set.setSetGroup(SetGroup.builder().id(parent.getId()).build());
        if (parent.getWorkout() != null && !Hibernate.isInitialized(parent.getWorkout())) {
          set.getSetGroup().setWorkout(Workout.builder().id(parent.getWorkout().getId()).build());
        }
      }
    }

    return set;
  }
}
