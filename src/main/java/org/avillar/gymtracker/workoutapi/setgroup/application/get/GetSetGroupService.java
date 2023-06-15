package org.avillar.gymtracker.workoutapi.setgroup.application.get;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.model.GetSetGroupResponse;

public interface GetSetGroupService {

  GetSetGroupResponse getSetGroup(UUID setGroupId, boolean full);

  List<GetSetGroupResponse> getAllUserAndExerciseSetGroups(UUID userId, UUID exerciseId);
}
