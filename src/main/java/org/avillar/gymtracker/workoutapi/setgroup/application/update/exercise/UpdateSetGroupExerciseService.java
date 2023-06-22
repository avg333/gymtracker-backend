package org.avillar.gymtracker.workoutapi.setgroup.application.update.exercise;

import java.util.UUID;

public interface UpdateSetGroupExerciseService {

  UUID execute(UUID setGroupId, UUID exerciseId);
}
