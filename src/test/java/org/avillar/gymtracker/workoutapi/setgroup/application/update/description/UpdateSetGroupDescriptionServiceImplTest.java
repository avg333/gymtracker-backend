package org.avillar.gymtracker.workoutapi.setgroup.application.update.description;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateSetGroupDescriptionServiceImplTest {

  @InjectMocks private UpdateSetGroupDescriptionServiceImpl updateSetGroupDescriptionService;
  @Mock private SetGroupDao setGroupDao;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @Test
  void updateOk() {
    final UUID setGroupId = UUID.randomUUID();
    final String description = "Description example 54.";

    final SetGroup setGroup = new SetGroup();
    setGroup.setId(setGroupId);
    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(List.of(setGroup));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(setGroup, AuthOperations.UPDATE);
    when(setGroupDao.save(Mockito.any(SetGroup.class))).thenAnswer(i -> i.getArguments()[0]);

    Assertions.assertEquals(
        description, updateSetGroupDescriptionService.execute(setGroupId, description));
  }

  @Test
  void postSameDescription() {
    final UUID setGroupId = UUID.randomUUID();
    final String description = "Description example 54.";

    final SetGroup setGroup = new SetGroup();
    setGroup.setId(setGroupId);
    setGroup.setDescription(description);

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(List.of(setGroup));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(setGroup, AuthOperations.UPDATE);

    verify(setGroupDao, never()).save(Mockito.any(SetGroup.class));
    Assertions.assertEquals(
        description, updateSetGroupDescriptionService.execute(setGroupId, description));
  }

  @Test
  void updateNotFound() {
    final UUID setGroupId = UUID.randomUUID();
    final String description = "Description example 54.";

    final SetGroup setGroup = new SetGroup();
    setGroup.setId(setGroupId);
    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class,
            () ->  updateSetGroupDescriptionService.execute(setGroupId, description));
    assertEquals(SetGroup.class.getSimpleName(), exception.getClassName());
    assertEquals(setGroupId, exception.getId());
  }

  @Test
  void updateNotPermission() {
    final UUID userId = UUID.randomUUID();
    final UUID setGroupId = UUID.randomUUID();
    final String description = "Description example 54.";

    final SetGroup setGroup = new SetGroup();
    setGroup.setId(setGroupId);

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(List.of(setGroup));
    doThrow(new IllegalAccessException(setGroup, AuthOperations.UPDATE, userId))
        .when(authWorkoutsService)
        .checkAccess(setGroup, AuthOperations.UPDATE);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class,
            () -> updateSetGroupDescriptionService.execute(setGroupId, description));
    assertEquals(SetGroup.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(setGroupId, exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(AuthOperations.UPDATE, exception.getAuthOperations());
  }
}
