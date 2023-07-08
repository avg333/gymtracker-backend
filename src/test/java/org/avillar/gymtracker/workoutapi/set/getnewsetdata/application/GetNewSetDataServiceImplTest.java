package org.avillar.gymtracker.workoutapi.set.getnewsetdata.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetDao;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.application.mapper.GetNewSetDataServiceMapperImpl;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetNewSetDataServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetNewSetDataServiceImpl getNewSetDataService;

  @Mock private SetDao setDao;
  @Mock private SetGroupDao setGroupDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private GetNewSetDataServiceMapperImpl getNewSetDataServiceMapper;

  @Test
  void getOkResultSetGroup() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    setGroup.setSets(easyRandom.objects(Set.class, 5).collect(Collectors.toSet()));

    when(setGroupDao.getSetGroupFullByIds(List.of(setGroup.getId()))).thenReturn(List.of(setGroup));
    doNothing().when(authWorkoutsService).checkAccess(setGroup, AuthOperations.READ);

    final Set expected =
        setGroup.getSets().stream().max(Comparator.comparingInt(Set::getListOrder)).get();
    final var result = getNewSetDataService.execute(setGroup.getId());
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void getOkResultSecondQuery() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    setGroup.setSets(Collections.emptySet());
    final Set expected = easyRandom.nextObject(Set.class);

    when(setGroupDao.getSetGroupFullByIds(List.of(setGroup.getId()))).thenReturn(List.of(setGroup));
    doNothing().when(authWorkoutsService).checkAccess(setGroup, AuthOperations.READ);
    when(setDao.findLastSetForExerciseAndUserAux(
            setGroup.getWorkout().getUserId(),
            setGroup.getExerciseId(),
            setGroup.getWorkout().getDate()))
        .thenReturn(List.of(expected));

    final var result = getNewSetDataService.execute(setGroup.getId());
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void getNotFoundResultSecondQuery() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    setGroup.setSets(Collections.emptySet());

    when(setGroupDao.getSetGroupFullByIds(List.of(setGroup.getId()))).thenReturn(List.of(setGroup));
    doNothing().when(authWorkoutsService).checkAccess(setGroup, AuthOperations.READ);
    when(setDao.findLastSetForExerciseAndUserAux(
            setGroup.getWorkout().getUserId(),
            setGroup.getExerciseId(),
            setGroup.getWorkout().getDate()))
        .thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        assertThrows(
            EntityNotFoundException.class, () -> getNewSetDataService.execute(setGroup.getId()));
    assertEquals(Set.class.getSimpleName(), exception.getClassName());
    assertEquals("exerciseId", exception.getSearchParam());
    assertEquals(setGroup.getExerciseId(), exception.getId());
  }

  @Test
  void setGroupNotFound() {
    final UUID setGroupId = UUID.randomUUID();

    when(setGroupDao.getSetGroupFullByIds(List.of(setGroupId))).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        assertThrows(EntityNotFoundException.class, () -> getNewSetDataService.execute(setGroupId));
    assertEquals(SetGroup.class.getSimpleName(), exception.getClassName());
    assertEquals(setGroupId, exception.getId());
  }

  @Test
  void getNotPermission() {
    final UUID userId = UUID.randomUUID();
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    final AuthOperations readOperation = AuthOperations.READ;

    when(setGroupDao.getSetGroupFullByIds(List.of(setGroup.getId()))).thenReturn(List.of(setGroup));
    doThrow(new IllegalAccessException(setGroup, readOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(setGroup, readOperation);

    final IllegalAccessException exception =
        assertThrows(
            IllegalAccessException.class, () -> getNewSetDataService.execute(setGroup.getId()));
    assertEquals(SetGroup.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(setGroup.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(readOperation, exception.getAuthOperations());
  }
}
