package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.application;

import java.util.UUID;

public interface UpdateSetGroupDescriptionService {

  String execute(UUID setGroupId, String description);
}
