package org.avillar.gymtracker.workoutapi.common.facade.set;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;

public interface SetFacade {

  Set getSetFull(UUID setId) throws SetNotFoundException;

  List<Set> getSetsBySetGroupId(UUID setGroupId);

  Set getSetGroupExerciseHistory(final SetGroup setGroup);

  Set saveSet(Set set);

  List<Set> saveSets(List<Set> sets);

  void deleteSet(UUID setId);
}
