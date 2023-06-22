package org.avillar.gymtracker.workoutapi.set.application.post;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetRequestApplication;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetResponseApplication;

public interface PostSetService {
  PostSetResponseApplication execute(
      UUID setGroupId, PostSetRequestApplication postSetRequestApplication);
}
