package org.avillar.gymtracker.workoutapi.setgroup.application.post;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.application.post.model.PostSetGroupRequest;
import org.avillar.gymtracker.workoutapi.setgroup.application.post.model.PostSetGroupResponse;

public interface PostSetGroupService {

  PostSetGroupResponse post(UUID workoutId, PostSetGroupRequest postSetGroupRequest);
}
