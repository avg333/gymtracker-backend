package org.avillar.gymtracker.workoutapi.setgroup.application.post;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.application.post.model.PostSetGroupRequestApplication;
import org.avillar.gymtracker.workoutapi.setgroup.application.post.model.PostSetGroupResponseApplication;

public interface PostSetGroupService {

  PostSetGroupResponseApplication post(UUID workoutId, PostSetGroupRequestApplication postSetGroupRequestApplication);
}
