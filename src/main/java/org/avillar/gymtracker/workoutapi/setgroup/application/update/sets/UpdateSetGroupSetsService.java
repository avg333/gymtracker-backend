package org.avillar.gymtracker.workoutapi.setgroup.application.update.sets;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.sets.model.UpdateSetGroupSetsResponse;

public interface UpdateSetGroupSetsService {

  UpdateSetGroupSetsResponse update(UUID setGroupDestinationId, UUID setGroupSourceId);
}
