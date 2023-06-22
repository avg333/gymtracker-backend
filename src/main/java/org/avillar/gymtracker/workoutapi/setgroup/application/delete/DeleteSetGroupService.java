package org.avillar.gymtracker.workoutapi.setgroup.application.delete;

import java.util.UUID;

public interface DeleteSetGroupService {

  void execute(UUID setGroupId);
}
