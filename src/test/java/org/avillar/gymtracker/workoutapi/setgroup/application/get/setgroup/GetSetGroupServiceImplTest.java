package org.avillar.gymtracker.workoutapi.setgroup.application.get.setgroup;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.setgroup.mapper.GetSetGroupServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.setgroup.model.GetSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetSetGroupServiceImplTest {

  private GetSetGroupService getSetGroupService;

  @Mock private SetGroupDao setGroupDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private GetSetGroupServiceMapperImpl getSetGroupServiceMapper;

  @BeforeEach
  void beforeEach() {
    getSetGroupService =
        new GetSetGroupServiceImpl(setGroupDao, authWorkoutsService, getSetGroupServiceMapper);
  }

  @Test
  void getOk() {
    final UUID setGroupId = UUID.randomUUID();
    final SetGroup setGroup = new SetGroup();
    final int listOrder = 3;
    final String description = "Description example 54.";
    final UUID exerciseId = UUID.randomUUID();
    final Workout workout = new Workout();
    final UUID workoutId = UUID.randomUUID();
    final java.util.Set<Set> sets = java.util.Set.of(new Set(), new Set());
    workout.setId(workoutId);
    setGroup.setWorkout(workout);
    setGroup.setId(setGroupId);
    setGroup.setListOrder(listOrder);
    setGroup.setDescription(description);
    setGroup.setExerciseId(exerciseId);
    setGroup.setSets(sets);

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(List.of(setGroup));
    Mockito.doNothing().when(authWorkoutsService).checkAccess(setGroup, AuthOperations.READ);

    final GetSetGroupResponseApplication getSetGroupResponseApplication =
        getSetGroupService.execute(setGroupId);

    Assertions.assertEquals(setGroupId, getSetGroupResponseApplication.getId());
    Assertions.assertEquals(listOrder, getSetGroupResponseApplication.getListOrder());
    Assertions.assertEquals(description, getSetGroupResponseApplication.getDescription());
    Assertions.assertEquals(exerciseId, getSetGroupResponseApplication.getExerciseId());
    Assertions.assertEquals(workoutId, getSetGroupResponseApplication.getWorkout().getId());
    Assertions.assertEquals(sets.size(), getSetGroupResponseApplication.getSets().size());
  }

  @Test
  void getNotFound() {
    final UUID setGroupId = UUID.randomUUID();

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class, () -> getSetGroupService.execute(setGroupId));
    assertEquals(SetGroup.class.getSimpleName(), exception.getClassName());
    assertEquals(setGroupId, exception.getId());
  }

  @Test
  void getNotPermission() {
    final UUID userId = UUID.randomUUID();
    final UUID setGroupId = UUID.randomUUID();
    final SetGroup setGroup = new SetGroup();
    setGroup.setId(setGroupId);

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(List.of(setGroup));
    doThrow(new IllegalAccessException(setGroup, AuthOperations.READ, userId))
        .when(authWorkoutsService)
        .checkAccess(setGroup, AuthOperations.READ);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class, () -> getSetGroupService.execute(setGroupId));
    assertEquals(SetGroup.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(setGroupId, exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(AuthOperations.READ, exception.getAuthOperations());
  }
}
