package org.avillar.gymtracker.workoutapi.set.getset.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;

public interface GetSetService {

  Set execute(UUID setId) throws SetNotFoundException, WorkoutIllegalAccessException;
}
