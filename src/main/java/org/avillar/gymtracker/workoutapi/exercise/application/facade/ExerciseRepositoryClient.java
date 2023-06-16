package org.avillar.gymtracker.workoutapi.exercise.application.facade;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.exercise.application.model.GetExerciseResponse;

public interface ExerciseRepositoryClient {

  GetExerciseResponse getExerciseById(UUID exerciseId);

  List<GetExerciseResponse> getExerciseByIds(Set<UUID> exerciseIds);
}
