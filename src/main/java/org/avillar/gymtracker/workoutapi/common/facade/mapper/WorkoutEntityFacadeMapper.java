package org.avillar.gymtracker.workoutapi.common.facade.mapper;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.model.SetEntity;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.model.SetGroupEntity;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.model.WorkoutEntity;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;

public interface WorkoutEntityFacadeMapper {

  Map<LocalDate, UUID> mapMap(Map<Date, UUID> workoutsIdAndDate);

  List<Workout> mapWorkouts(List<WorkoutEntity> workoutEntity);

  List<SetGroup> mapSetGroups(Collection<SetGroupEntity> setGroupEntities);

  List<Set> mapSets(Collection<SetEntity> setEntities);

  Workout map(WorkoutEntity workoutEntity);

  SetGroup map(SetGroupEntity setGroupEntity);

  Set map(SetEntity setEntity);
}
