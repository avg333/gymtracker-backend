package org.avillar.gymtracker.workoutapi.set.getnewsetdata.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;

public interface GetNewSetDataService {

  Set execute(UUID setGroupId) throws SetGroupNotFoundException, WorkoutIllegalAccessException;
}
