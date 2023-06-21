package org.avillar.gymtracker.workoutapi.set.application.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.application.post.mapper.PostSetServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetRequestApplication;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.avillar.gymtracker.workoutapi.set.domain.SetDao;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroupDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostSetServiceImplTest {

  private PostSetService postSetService;

  @Mock private SetDao setDao;
  @Mock private SetGroupDao setGroupDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private PostSetServiceMapperImpl postSetServiceMapper;

  @BeforeEach
  void beforeEach() {
    postSetService =
        new PostSetServiceImpl(setDao, setGroupDao, authWorkoutsService, postSetServiceMapper);
  }

  @Test
  void postOk() {
    final UUID setGroupId = UUID.randomUUID();
    final String description = "Description example 54.";
    final double weight = 115.2;
    final double rir = 8.0;
    final int reps = 6;
    final PostSetRequestApplication postSetRequestApplication = new PostSetRequestApplication();
    postSetRequestApplication.setDescription(description);
    postSetRequestApplication.setWeight(weight);
    postSetRequestApplication.setRir(rir);
    postSetRequestApplication.setReps(reps);

    final UUID setId = UUID.randomUUID();
    final Set set = new Set();
    set.setId(setId);
    final SetGroup setGroup = new SetGroup();
    setGroup.setId(setGroupId);
    final java.util.Set<Set> setGroups = java.util.Set.of(new Set(), new Set());
    setGroup.setSets(setGroups);

    when(setGroupDao.getSetGroupFullByIds(List.of(setGroupId))).thenReturn(List.of(setGroup));
    Mockito.doNothing()
        .when(authWorkoutsService)
        .checkAccess(Mockito.any(Set.class), eq(AuthOperations.CREATE));
    when(setDao.save(Mockito.any(Set.class))).thenAnswer(i -> i.getArguments()[0]);

    final PostSetResponseApplication postSetResponseApplication =
        postSetService.post(setGroupId, postSetRequestApplication);
    Assertions.assertEquals(setGroupId, postSetResponseApplication.getSetGroup().getId());
    Assertions.assertEquals(description, postSetResponseApplication.getDescription());
    Assertions.assertEquals(rir, postSetResponseApplication.getRir());
    Assertions.assertEquals(reps, postSetResponseApplication.getReps());
    Assertions.assertEquals(weight, postSetResponseApplication.getWeight());
  }

  @Test
  void postNotFound() {
    final UUID setGroupId = UUID.randomUUID();
    final String description = "Description example 54.";
    final double weight = 115.2;
    final double rir = 8.0;
    final int reps = 6;
    final PostSetRequestApplication postSetRequestApplication = new PostSetRequestApplication();
    postSetRequestApplication.setDescription(description);
    postSetRequestApplication.setWeight(weight);
    postSetRequestApplication.setRir(rir);
    postSetRequestApplication.setReps(reps);

    final UUID setId = UUID.randomUUID();
    final Set set = new Set();
    set.setId(setId);
    final SetGroup setGroup = new SetGroup();
    setGroup.setId(setGroupId);
    final java.util.Set<Set> setGroups = java.util.Set.of(new Set(), new Set());
    setGroup.setSets(setGroups);

    when(setGroupDao.getSetGroupFullByIds(List.of(setGroupId))).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class,
            () -> postSetService.post(setGroupId, postSetRequestApplication));
    assertEquals(SetGroup.class.getSimpleName(), exception.getClassName());
    assertEquals(setGroupId, exception.getId());
  }

  @Test
  void deleteNotPermission() {
    final UUID userId = UUID.randomUUID();
    final UUID setGroupId = UUID.randomUUID();
    final String description = "Description example 54.";
    final double weight = 115.2;
    final double rir = 8.0;
    final int reps = 6;
    final PostSetRequestApplication postSetRequestApplication = new PostSetRequestApplication();
    postSetRequestApplication.setDescription(description);
    postSetRequestApplication.setWeight(weight);
    postSetRequestApplication.setRir(rir);
    postSetRequestApplication.setReps(reps);

    final Set set = new Set();
    final SetGroup setGroup = new SetGroup();
    setGroup.setId(setGroupId);
    final java.util.Set<Set> setGroups = java.util.Set.of(new Set(), new Set());
    setGroup.setSets(setGroups);

    when(setGroupDao.getSetGroupFullByIds(List.of(setGroupId))).thenReturn(List.of(setGroup));
    doThrow(new IllegalAccessException(set, AuthOperations.CREATE, userId))
        .when(authWorkoutsService)
        .checkAccess(Mockito.any(Set.class), eq(AuthOperations.CREATE));

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class,
            () -> postSetService.post(setGroupId, postSetRequestApplication));
    assertEquals(Set.class.getSimpleName(), exception.getEntityClassName());
    assertNull(exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(AuthOperations.CREATE, exception.getAuthOperations());
  }
}
