package org.avillar.gymtracker.workoutapi.setgroup.application.get;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.model.GetSetGroupResponseApplication;

public interface GetSetGroupService {

  GetSetGroupResponseApplication getSetGroup(UUID setGroupId, boolean full);

  List<GetSetGroupResponseApplication> getAllUserAndExerciseSetGroups(UUID userId, UUID exerciseId);
}
