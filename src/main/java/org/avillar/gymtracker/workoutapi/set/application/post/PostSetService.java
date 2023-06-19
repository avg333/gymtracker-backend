package org.avillar.gymtracker.workoutapi.set.application.post;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetRequestApplication;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetResponseApplication;

public interface PostSetService {
  PostSetResponseApplication post(
      UUID setGroupId, PostSetRequestApplication postSetRequestApplication);
}
