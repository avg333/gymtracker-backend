package org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.application;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;

public interface GetWorkoutsDateAndIdService {

  Map<LocalDate, UUID> execute(UUID userId, UUID exerciseId) throws WorkoutIllegalAccessException;
}
