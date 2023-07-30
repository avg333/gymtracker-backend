package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.mapper.GetSetGroupServiceMapper;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.model.GetSetGroupResponseApplication;
import org.jeasy.random.EasyRandom;
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
class GetSetGroupServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetSetGroupServiceImpl getSetGroupService;

  @Mock private SetGroupDao setGroupDao;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @Spy
  private final GetSetGroupServiceMapper getSetGroupServiceMapper =
      Mappers.getMapper(GetSetGroupServiceMapper.class);

  @Test
  void getOk() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);

    when(setGroupDao.getSetGroupWithWorkoutById(setGroup.getId())).thenReturn(List.of(setGroup));
    doNothing().when(authWorkoutsService).checkAccess(setGroup, AuthOperations.READ);

    final GetSetGroupResponseApplication result = getSetGroupService.execute(setGroup.getId());
    assertThat(result).usingRecursiveComparison().isEqualTo(setGroup);
  }

  @Test
  void getNotFound() {
    final UUID setGroupId = UUID.randomUUID();

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        assertThrows(EntityNotFoundException.class, () -> getSetGroupService.execute(setGroupId));
    assertEquals(SetGroup.class.getSimpleName(), exception.getClassName());
    assertEquals(setGroupId, exception.getId());
  }

  @Test
  void getNotPermission() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    final UUID userId = UUID.randomUUID();
    final AuthOperations readOperation = AuthOperations.READ;

    when(setGroupDao.getSetGroupWithWorkoutById(setGroup.getId())).thenReturn(List.of(setGroup));
    doThrow(new IllegalAccessException(setGroup, readOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(setGroup, readOperation);

    final IllegalAccessException exception =
        assertThrows(
            IllegalAccessException.class, () -> getSetGroupService.execute(setGroup.getId()));
    assertEquals(SetGroup.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(setGroup.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(readOperation, exception.getAuthOperations());
  }
}
