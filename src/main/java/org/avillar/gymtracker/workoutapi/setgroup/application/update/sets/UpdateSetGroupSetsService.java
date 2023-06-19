package org.avillar.gymtracker.workoutapi.setgroup.application.update.sets;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.sets.model.UpdateSetGroupSetsResponseApplication;

public interface UpdateSetGroupSetsService {

  UpdateSetGroupSetsResponseApplication update(UUID setGroupDestinationId, UUID setGroupSourceId);
}
