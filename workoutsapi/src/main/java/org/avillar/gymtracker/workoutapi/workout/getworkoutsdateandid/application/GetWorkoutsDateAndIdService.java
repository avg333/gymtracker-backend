package org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.application;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;

public interface GetWorkoutsDateAndIdService {

  Map<Date, UUID> execute(UUID userId, UUID exerciseId) throws IllegalAccessException;
}
