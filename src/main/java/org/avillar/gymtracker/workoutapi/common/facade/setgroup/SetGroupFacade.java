package org.avillar.gymtracker.workoutapi.common.facade.setgroup;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;

public interface SetGroupFacade {

  SetGroup getSetGroupFull(UUID setGroupId) throws SetGroupNotFoundException;

  List<SetGroup> getSetGroupFullByIds(Collection<UUID> setGroupIds);

  SetGroup getSetGroupWithWorkout(UUID setGroupId) throws SetGroupNotFoundException;

  List<SetGroup> getSetGroupsByWorkoutId(UUID workoutId);

  List<SetGroup> getSetGroupsFullByUserIdAndExerciseId(UUID userId, UUID exerciseId);

  SetGroup saveSetGroup(SetGroup setGroup);

  List<SetGroup> saveSetGroups(List<SetGroup> setGroups);

  void deleteSetGroup(UUID setGroupId);
}
