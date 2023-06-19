package org.avillar.gymtracker.workoutapi.workout.infrastructure.post;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.application.post.PostWorkoutService;
import org.avillar.gymtracker.workoutapi.workout.application.post.model.PostWorkoutRequestApplication;
import org.avillar.gymtracker.workoutapi.workout.application.post.model.PostWorkoutResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.post.mapper.PostWorkoutControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.post.model.PostWorkoutRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.post.model.PostWorkoutResponseInfrastructure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostWorkoutControllerTest {

  @InjectMocks private PostWorkoutController postWorkoutController;

  @Mock private PostWorkoutService postWorkoutService;

  @Mock
  private PostWorkoutControllerMapper postWorkoutControllerMapper; // TODO No mockear el mapper

  @Test
  void postWorkout() {
    final UUID userId = UUID.randomUUID();
    final Date date = new Date();
    final String description = "Description example 54.";
    final PostWorkoutRequestInfrastructure postWorkoutRequestInfrastructure =
        new PostWorkoutRequestInfrastructure();
    postWorkoutRequestInfrastructure.setDate(date);
    postWorkoutRequestInfrastructure.setDescription(description);

    final UUID workoutId = UUID.randomUUID();
    when(postWorkoutService.post(eq(userId), any(PostWorkoutRequestApplication.class)))
        .thenReturn(new PostWorkoutResponseApplication(workoutId, date, description, userId));
    when(postWorkoutControllerMapper.postRequest(any(PostWorkoutRequestInfrastructure.class)))
        .thenReturn(new PostWorkoutRequestApplication());
    when(postWorkoutControllerMapper.postResponse(any(PostWorkoutResponseApplication.class)))
        .thenReturn(new PostWorkoutResponseInfrastructure(workoutId, date, description, userId));

    final PostWorkoutResponseInfrastructure postWorkoutResponseInfrastructure =
        postWorkoutController.postWorkout(userId, postWorkoutRequestInfrastructure).getBody();
    Assertions.assertEquals(workoutId, postWorkoutResponseInfrastructure.getId());
    Assertions.assertEquals(date, postWorkoutResponseInfrastructure.getDate());
    Assertions.assertEquals(description, postWorkoutResponseInfrastructure.getDescription());
    Assertions.assertEquals(userId, postWorkoutResponseInfrastructure.getUserId());
  }
}
