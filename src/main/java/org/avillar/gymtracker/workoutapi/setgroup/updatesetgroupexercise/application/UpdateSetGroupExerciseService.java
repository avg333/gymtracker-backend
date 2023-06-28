package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.application;

import java.util.UUID;

public interface UpdateSetGroupExerciseService {

  UUID execute(UUID setGroupId, UUID exerciseId);
}
