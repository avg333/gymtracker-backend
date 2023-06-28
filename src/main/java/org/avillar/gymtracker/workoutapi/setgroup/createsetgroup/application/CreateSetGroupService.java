package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupRequestApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupResponseApplication;

public interface CreateSetGroupService {

  CreateSetGroupResponseApplication execute(
      UUID workoutId, CreateSetGroupRequestApplication createSetGroupRequestApplication);
}
