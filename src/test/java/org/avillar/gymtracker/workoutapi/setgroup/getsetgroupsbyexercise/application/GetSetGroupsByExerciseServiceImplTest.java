package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.mapper.GetSetGroupsByExerciseServiceMapper;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.model.GetSetGroupsByExerciseResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class GetSetGroupsByExerciseServiceImplTest {

  private static final int LIST_SIZE = 5;

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetSetGroupsByExerciseServiceImpl getSetGroupsByExerciseService;
  @Mock private SetGroupDao setGroupDao;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @Spy
  private final GetSetGroupsByExerciseServiceMapper getSetGroupsByExerciseServiceMapper =
      Mappers.getMapper(GetSetGroupsByExerciseServiceMapper.class);

  @Test
  void getOk() {
    final UUID userId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();
    final List<SetGroup> expected = easyRandom.objects(SetGroup.class, LIST_SIZE).toList();
    expected.forEach(
        setGroup ->
            setGroup.setSets(easyRandom.objects(Set.class, LIST_SIZE).collect(Collectors.toSet())));

    when(setGroupDao.getSetGroupsFullByUserIdAndExerciseId(userId, exerciseId))
        .thenReturn(expected);
    doNothing().when(authWorkoutsService).checkAccess(any(SetGroup.class), eq(AuthOperations.READ));

    final List<GetSetGroupsByExerciseResponseApplication> result =
        getSetGroupsByExerciseService.execute(userId, exerciseId);
    assertThat(result).hasSameSizeAs(expected);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void getNotPermission() {
    final UUID userId = UUID.randomUUID();
    final Workout workout = new Workout();
    workout.setUserId(userId);
    final SetGroup setGroup = new SetGroup();
    setGroup.setWorkout(workout);
    final AuthOperations readOperation = AuthOperations.READ;

    doThrow(new IllegalAccessException(setGroup, readOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(any(SetGroup.class), eq(readOperation));

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class,
            () -> getSetGroupsByExerciseService.execute(userId, UUID.randomUUID()));
    assertEquals(SetGroup.class.getSimpleName(), exception.getEntityClassName());
    assertNull(exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(readOperation, exception.getAuthOperations());
    verify(setGroupDao, never()).getSetGroupsFullByUserIdAndExerciseId(any(), any());
  }
}
