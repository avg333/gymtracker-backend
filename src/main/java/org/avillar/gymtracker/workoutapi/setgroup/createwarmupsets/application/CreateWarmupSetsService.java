package org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.application.model.CreateWarmupSetsRequestApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.application.model.CreateWarmupSetsResponseApplication;

public interface CreateWarmupSetsService {

  CreateWarmupSetsResponseApplication execute(
      UUID setGroupId, CreateWarmupSetsRequestApplication request);
}
