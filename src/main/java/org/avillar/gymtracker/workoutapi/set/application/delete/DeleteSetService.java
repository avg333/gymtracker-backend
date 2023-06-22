package org.avillar.gymtracker.workoutapi.set.application.delete;

import java.util.UUID;

public interface DeleteSetService {

  void execute(UUID setId);
}
