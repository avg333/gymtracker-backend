package org.avillar.gymtracker.workoutapi.setgroup.application.delete;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteSetGroupServiceImplTest {

  private final UUID setGroupId = UUID.randomUUID();
  private final UUID workoutId = UUID.randomUUID();

  @InjectMocks private DeleteSetGroupServiceImpl deleteSetGroupService;

  @Mock private SetGroupDao setGroupDao;

  @Mock private AuthWorkoutsService authWorkoutsService;

  @Test
  void deleteOk() {
    final SetGroup setGroup = new SetGroup();
    setGroup.setId(setGroupId);
    setGroup.setListOrder(0);
    final Workout workout = new Workout();
    workout.setId(workoutId);
    setGroup.setWorkout(workout);

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(List.of(setGroup));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(setGroup, AuthOperations.DELETE);
    when(setGroupDao.getSetGroupsByWorkoutId(workoutId)).thenReturn(Set.of(setGroup));

    Assertions.assertDoesNotThrow(() -> deleteSetGroupService.execute(setGroupId));
    // TODO Revisar caso de lista vacia y no vacia
  }

  @Test
  void deleteNotFound() {
    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class, () -> deleteSetGroupService.execute(setGroupId));
    assertEquals(SetGroup.class.getSimpleName(), exception.getClassName());
    assertEquals(setGroupId, exception.getId());
  }

  @Test
  void deleteNotPermission() {
    final SetGroup setGroup = new SetGroup();
    setGroup.setId(setGroupId);
    final UUID userId = UUID.randomUUID();

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(List.of(setGroup));
    doThrow(new IllegalAccessException(setGroup, AuthOperations.DELETE, userId))
        .when(authWorkoutsService)
        .checkAccess(setGroup, AuthOperations.DELETE);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class, () -> deleteSetGroupService.execute(setGroupId));
    assertEquals(SetGroup.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(setGroupId, exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(AuthOperations.DELETE, exception.getAuthOperations());
  }
}
