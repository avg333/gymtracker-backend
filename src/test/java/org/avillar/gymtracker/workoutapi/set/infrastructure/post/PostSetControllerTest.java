package org.avillar.gymtracker.workoutapi.set.infrastructure.post;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.set.application.post.PostSetService;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetRequestApplication;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.infrastructure.post.mapper.PostSetControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.set.infrastructure.post.model.PostSetRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.set.infrastructure.post.model.PostSetResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostSetControllerTest {

  private final EasyRandom easyRandom = new EasyRandom();

  private PostSetController postSetController;

  @Mock private PostSetService postSetService;

  @Spy private PostSetControllerMapperImpl postSetControllerMapper;

  @BeforeEach
  void beforeEach() {
    postSetController = new PostSetController(postSetService, postSetControllerMapper);
  }

  @Test
  void postSet() {
    final PostSetResponseApplication postSetResponseApplication =
        easyRandom.nextObject(PostSetResponseApplication.class);
    final PostSetRequestInfrastructure postSetRequestInfrastructure =
        new PostSetRequestInfrastructure();
    postSetRequestInfrastructure.setReps(postSetResponseApplication.getReps());
    postSetRequestInfrastructure.setRir(postSetResponseApplication.getRir());
    postSetRequestInfrastructure.setWeight(postSetResponseApplication.getWeight());
    postSetRequestInfrastructure.setDescription(postSetResponseApplication.getDescription());

    when(postSetService.execute(
            eq(postSetResponseApplication.getSetGroup().getId()),
            any(PostSetRequestApplication.class)))
        .thenReturn(postSetResponseApplication);

    final PostSetResponseInfrastructure postSetResponseInfrastructure =
        postSetController
            .post(postSetResponseApplication.getSetGroup().getId(), postSetRequestInfrastructure)
            .getBody();
    Assertions.assertEquals(
        postSetResponseApplication.getId(), postSetResponseInfrastructure.getId());
    Assertions.assertEquals(
        postSetResponseApplication.getListOrder(), postSetResponseInfrastructure.getListOrder());
    Assertions.assertEquals(
        postSetResponseApplication.getDescription(),
        postSetResponseInfrastructure.getDescription());
    Assertions.assertEquals(
        postSetResponseApplication.getRir(), postSetResponseInfrastructure.getRir());
    Assertions.assertEquals(
        postSetResponseApplication.getReps(), postSetResponseInfrastructure.getReps());
    Assertions.assertEquals(
        postSetResponseApplication.getWeight(), postSetResponseInfrastructure.getWeight());
    Assertions.assertEquals(
        postSetResponseApplication.getSetGroup().getId(),
        postSetResponseInfrastructure.getSetGroup().getId());
  }
}
