package org.avillar.gymtracker.workoutapi.setgroup.application.update.description;

import java.util.UUID;

public interface UpdateSetGroupDescriptionService {

  String update(UUID setGroupId, String description);
}
