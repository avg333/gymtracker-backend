package org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisebyid;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisebyid.model.GetExerciseByIdApplicationResponse;

public interface GetExerciseByIdService {

  GetExerciseByIdApplicationResponse execute(UUID exerciseId);

  List<GetExerciseByIdApplicationResponse> execute(Set<UUID> exerciseIds);
}
