package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.post;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.application.post.PostSetGroupService;
import org.avillar.gymtracker.workoutapi.setgroup.application.post.model.PostSetGroupRequestApplication;
import org.avillar.gymtracker.workoutapi.setgroup.application.post.model.PostSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.application.post.model.PostSetGroupResponseApplication.Workout;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.post.mapper.PostSetGroupControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.post.model.PostSetGroupRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.post.model.PostSetGroupResponseInfrastructure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostSetGroupControllerTest {

  @InjectMocks private PostSetGroupController postSetGroupController;

  @Mock private PostSetGroupService postSetGroupService;

  @Mock
  private PostSetGroupControllerMapperImpl
      postSetGroupControllerMapper; // TODO No mockear el mapper

  @Test
  void post() {
    final UUID workoutId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();
    final String description = "Description example 54.";
    final PostSetGroupRequestInfrastructure postWorkoutRequestInfrastructure =
        new PostSetGroupRequestInfrastructure();
    postWorkoutRequestInfrastructure.setExerciseId(exerciseId);
    postWorkoutRequestInfrastructure.setDescription(description);

    final UUID setGroupId = UUID.randomUUID();
    final int listOrder = 0;
    when(postSetGroupService.post(eq(workoutId), any(PostSetGroupRequestApplication.class)))
        .thenReturn(
            new PostSetGroupResponseApplication(
                setGroupId, listOrder, description, exerciseId, new Workout(workoutId)));
    when(postSetGroupControllerMapper.postRequest(postWorkoutRequestInfrastructure))
        .thenReturn(new PostSetGroupRequestApplication());
    when(postSetGroupControllerMapper.postResponse(any(PostSetGroupResponseApplication.class)))
        .thenReturn(
            new PostSetGroupResponseInfrastructure(
                setGroupId,
                listOrder,
                description,
                exerciseId,
                new PostSetGroupResponseInfrastructure.Workout(workoutId)));

    final PostSetGroupResponseInfrastructure postSetGroupResponseInfrastructure =
        postSetGroupController.post(workoutId, postWorkoutRequestInfrastructure).getBody();
    Assertions.assertEquals(setGroupId, postSetGroupResponseInfrastructure.getId());
    Assertions.assertEquals(listOrder, postSetGroupResponseInfrastructure.getListOrder());
    Assertions.assertEquals(exerciseId, postSetGroupResponseInfrastructure.getExerciseId());
    Assertions.assertEquals(description, postSetGroupResponseInfrastructure.getDescription());
    Assertions.assertEquals(workoutId, postSetGroupResponseInfrastructure.getWorkout().getId());
  }
}
