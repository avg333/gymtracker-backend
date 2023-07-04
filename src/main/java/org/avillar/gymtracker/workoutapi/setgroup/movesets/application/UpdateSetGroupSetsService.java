package org.avillar.gymtracker.workoutapi.setgroup.movesets.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.application.model.UpdateSetGroupSetsResponseApplication;

public interface UpdateSetGroupSetsService {

  UpdateSetGroupSetsResponseApplication execute(UUID setGroupDestinationId, UUID setGroupSourceId);
}
