package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.mapper.GetSetGroupsByExerciseServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.model.GetSetGroupsByExerciseResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetSetGroupsByExerciseServiceImplTest {

  private EasyRandom easyRandom = new EasyRandom();

  @InjectMocks GetSetGroupsByExerciseServiceImpl getSetGroupsByExerciseService;

  @Mock private SetGroupDao setGroupDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private GetSetGroupsByExerciseServiceMapperImpl getSetGroupsByExerciseServiceMapper;

  @Test
  void getOk() {
    final UUID userId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();
    final List<SetGroup> expected = easyRandom.objects(SetGroup.class, 5).toList();

    when(setGroupDao.getSetGroupsFullByUserIdAndExerciseId(userId, exerciseId))
        .thenReturn(expected);
    doNothing().when(authWorkoutsService).checkAccess(any(SetGroup.class), eq(AuthOperations.READ));

    final List<GetSetGroupsByExerciseResponseApplication> result =
        getSetGroupsByExerciseService.execute(userId, exerciseId);
    assertEquals(expected.size(), result.size());
    assertEquals(expected.get(0).getId(), result.get(0).getId());
    assertEquals(expected.get(0).getExerciseId(), result.get(0).getExerciseId());
    assertEquals(expected.get(0).getDescription(), result.get(0).getDescription());
    assertEquals(expected.get(0).getListOrder(), result.get(0).getListOrder());
    assertEquals(expected.get(0).getWorkout().getId(), result.get(0).getWorkout().getId());
    assertEquals(expected.get(0).getId(), result.get(0).getId());
  }

  @Test
  void getNotPermission() {
    final UUID userId = UUID.randomUUID();
    final Workout workout = new Workout();
    workout.setUserId(userId);
    final SetGroup setGroup = new SetGroup();
    setGroup.setWorkout(workout);
    final AuthOperations authOperation = AuthOperations.READ;

    doThrow(new IllegalAccessException(setGroup, authOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(any(SetGroup.class), eq(authOperation));

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class,
            () -> getSetGroupsByExerciseService.execute(userId, UUID.randomUUID()));
    assertEquals(SetGroup.class.getSimpleName(), exception.getEntityClassName());
    assertNull(exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(authOperation, exception.getAuthOperations());
    verify(setGroupDao, never()).getSetGroupsFullByUserIdAndExerciseId(any(), any());
  }
}
