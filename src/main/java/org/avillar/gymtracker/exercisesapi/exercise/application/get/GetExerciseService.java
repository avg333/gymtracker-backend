package org.avillar.gymtracker.exercisesapi.exercise.application.get;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.model.GetExerciseApplicationRequest;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.model.GetExerciseApplicationResponse;

public interface GetExerciseService {

  GetExerciseApplicationResponse getById(UUID exerciseId);

  List<GetExerciseApplicationResponse> getByIds(Set<UUID> exerciseId);

  List<GetExerciseApplicationResponse> getAllExercises(
      GetExerciseApplicationRequest getExerciseApplicationRequest);
}
