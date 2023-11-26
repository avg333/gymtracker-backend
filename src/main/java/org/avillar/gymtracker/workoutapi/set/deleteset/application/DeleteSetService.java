package org.avillar.gymtracker.workoutapi.set.deleteset.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;

public interface DeleteSetService {

  void execute(UUID setId) throws SetNotFoundException, WorkoutIllegalAccessException;
}
