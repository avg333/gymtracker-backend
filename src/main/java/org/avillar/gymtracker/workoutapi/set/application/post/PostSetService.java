package org.avillar.gymtracker.workoutapi.set.application.post;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetRequest;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetResponse;

public interface PostSetService {
  PostSetResponse post(UUID setGroupId, PostSetRequest postSetRequest);
}
