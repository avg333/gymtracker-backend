package org.avillar.gymtracker.workoutapi.exercise.application.facade;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.exercise.application.model.GetExerciseResponseFacade;

public interface ExerciseRepositoryClient {

  boolean canAccessExerciseById(UUID exerciseId);

  List<GetExerciseResponseFacade> getExerciseByIds(Set<UUID> exerciseIds);
}
