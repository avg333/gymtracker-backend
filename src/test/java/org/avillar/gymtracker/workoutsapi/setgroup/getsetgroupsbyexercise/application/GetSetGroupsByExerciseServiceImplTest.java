package org.avillar.gymtracker.workoutsapi.setgroup.getsetgroupsbyexercise.application;

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
import java.util.stream.Collectors;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.GetSetGroupsByExerciseServiceImpl;
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

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetSetGroupsByExerciseServiceImpl getSetGroupsByExerciseService;
  @Mock private SetGroupDao setGroupDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private GetSetGroupsByExerciseServiceMapperImpl getSetGroupsByExerciseServiceMapper;

  @Test
  void getOk() {
    final UUID userId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();
    final List<SetGroup> expected = easyRandom.objects(SetGroup.class, 5).toList();
    expected.forEach(
        setGroup -> setGroup.setSets(easyRandom.objects(Set.class, 5).collect(Collectors.toSet())));

    when(setGroupDao.getSetGroupsFullByUserIdAndExerciseId(userId, exerciseId))
        .thenReturn(expected);
    doNothing().when(authWorkoutsService).checkAccess(any(SetGroup.class), eq(AuthOperations.READ));

    final List<GetSetGroupsByExerciseResponseApplication> result =
        getSetGroupsByExerciseService.execute(userId, exerciseId);
    assertEquals(expected.size(), result.size());

    for (int i = 0; i < expected.size(); i++) {
      final var setGroupExpected = expected.get(i);
      final var setGroupResult = result.get(i);
      assertEquals(setGroupExpected.getId(), setGroupResult.getId());
      assertEquals(setGroupExpected.getDescription(), setGroupResult.getDescription());
      assertEquals(setGroupExpected.getListOrder(), setGroupResult.getListOrder());
      assertEquals(setGroupExpected.getExerciseId(), setGroupResult.getExerciseId());
      assertEquals(setGroupExpected.getWorkout().getId(), setGroupResult.getWorkout().getId());
      assertEquals(setGroupExpected.getSets().size(), setGroupResult.getSets().size());
      for (int k = 0; k < expected.size(); k++) {
        final var setExpected = setGroupExpected.getSets().stream().toList().get(k);
        final var setResult = setGroupResult.getSets().get(k);
        assertEquals(setExpected.getId(), setResult.getId());
        assertEquals(setExpected.getDescription(), setResult.getDescription());
        assertEquals(setExpected.getListOrder(), setResult.getListOrder());
        assertEquals(setExpected.getRir(), setResult.getRir());
        assertEquals(setExpected.getReps(), setResult.getReps());
        assertEquals(setExpected.getWeight(), setResult.getWeight());
      }
    }
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
