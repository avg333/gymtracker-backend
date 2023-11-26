package org.avillar.gymtracker.workoutapi.common.facade.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.model.SetEntity;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.model.SetGroupEntity;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.model.WorkoutEntity;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;

public interface WorkoutFacadeMapper {

  List<SetGroupEntity> mapSetGroups(List<SetGroup> setGroups);

  List<SetEntity> mapSets(List<Set> sets);

  WorkoutEntity map(Workout workout);

  SetGroupEntity map(SetGroup setGroup);

  SetEntity map(Set set);
}
