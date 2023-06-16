package org.avillar.gymtracker.exercisesapi.exercise.application.get;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.model.GetExerciseRequest;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.model.GetExerciseResponse;

public interface GetExerciseService {

  GetExerciseResponse getById(UUID exerciseId);

  List<GetExerciseResponse> getByIds(Set<UUID> exerciseId);

  List<GetExerciseResponse> getAllExercises(GetExerciseRequest getExerciseRequest);
}
