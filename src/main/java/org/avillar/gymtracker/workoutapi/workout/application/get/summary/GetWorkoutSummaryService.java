package org.avillar.gymtracker.workoutapi.workout.application.get.summary;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.application.get.summary.model.GetWorkoutSummaryResponse;

public interface GetWorkoutSummaryService {

  GetWorkoutSummaryResponse getWorkoutSummary(UUID workoutId);
}
