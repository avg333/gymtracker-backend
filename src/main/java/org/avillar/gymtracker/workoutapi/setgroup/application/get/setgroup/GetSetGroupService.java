package org.avillar.gymtracker.workoutapi.setgroup.application.get.setgroup;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.setgroup.model.GetSetGroupResponseApplication;

public interface GetSetGroupService {

  GetSetGroupResponseApplication execute(UUID setGroupId);
}
