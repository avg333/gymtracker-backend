package org.avillar.gymtracker.workoutapi.workout.application.get.summary;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.application.get.summary.model.GetWorkoutSummaryResponseApplication;

public interface GetWorkoutSummaryService {

  GetWorkoutSummaryResponseApplication getWorkoutSummary(UUID workoutId);
}
