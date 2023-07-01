package org.avillar.gymtracker.workoutapi.setgroup.application.update.sets;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.sets.model.UpdateSetGroupSetsResponseApplication;

public interface UpdateSetGroupSetsService {

  UpdateSetGroupSetsResponseApplication execute(UUID setGroupDestinationId, UUID setGroupSourceId);
}
