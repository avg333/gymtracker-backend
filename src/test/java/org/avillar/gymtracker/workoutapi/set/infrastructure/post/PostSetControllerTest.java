package org.avillar.gymtracker.workoutapi.set.infrastructure.post;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.application.post.PostSetService;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetRequestApplication;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetResponseApplication.SetGroup;
import org.avillar.gymtracker.workoutapi.set.infrastructure.post.mapper.PostSetControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.set.infrastructure.post.model.PostSetRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.set.infrastructure.post.model.PostSetResponseInfrastructure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostSetControllerTest {

  private PostSetController postSetController;

  @Mock private PostSetService postSetService;

  @Spy private PostSetControllerMapperImpl postSetControllerMapper;

  @BeforeEach
  void beforeEach() {
    postSetController = new PostSetController(postSetService, postSetControllerMapper);
  }

  @Test
  void postSet() {
    final UUID setGroupId = UUID.randomUUID();
    final String description = "Description example 54.";
    final double weight = 115.2;
    final double rir = 8.0;
    final int reps = 6;
    final PostSetRequestInfrastructure postSetRequestInfrastructure =
        new PostSetRequestInfrastructure();
    postSetRequestInfrastructure.setReps(reps);
    postSetRequestInfrastructure.setRir(rir);
    postSetRequestInfrastructure.setWeight(weight);
    postSetRequestInfrastructure.setDescription(description);

    final UUID setId = UUID.randomUUID();
    final int listOrder = 0;
    when(postSetService.post(eq(setGroupId), any(PostSetRequestApplication.class)))
        .thenReturn(
            new PostSetResponseApplication(
                setId, listOrder, description, reps, rir, weight, new SetGroup(setGroupId)));

    final PostSetResponseInfrastructure postSetResponseInfrastructure =
        postSetController.postSet(setGroupId, postSetRequestInfrastructure).getBody();
    Assertions.assertEquals(setId, postSetResponseInfrastructure.getId());
    Assertions.assertEquals(listOrder, postSetResponseInfrastructure.getListOrder());
    Assertions.assertEquals(description, postSetResponseInfrastructure.getDescription());
    Assertions.assertEquals(rir, postSetResponseInfrastructure.getRir());
    Assertions.assertEquals(reps, postSetResponseInfrastructure.getReps());
    Assertions.assertEquals(weight, postSetResponseInfrastructure.getWeight());
    Assertions.assertEquals(setGroupId, postSetResponseInfrastructure.getSetGroup().getId());
  }
}
