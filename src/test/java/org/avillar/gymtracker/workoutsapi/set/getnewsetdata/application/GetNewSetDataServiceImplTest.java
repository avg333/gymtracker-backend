package org.avillar.gymtracker.workoutsapi.set.getnewsetdata.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
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
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.application.GetNewSetDataServiceImpl;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.application.mapper.GetNewSetDataServiceMapperImpl;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    when(setGroupDao.getSetGroupWithWorkoutById(setGroup.getId())).thenReturn(List.of(setGroup));
    doNothing().when(authWorkoutsService).checkAccess(setGroup, AuthOperations.READ);

    final Set expected =
        setGroup.getSets().stream().max(Comparator.comparingInt(Set::getListOrder)).get();
    final var result = getNewSetDataService.execute(setGroup.getId());
    assertEquals(expected.getWeight(), result.getWeight());
    assertEquals(expected.getRir(), result.getRir());
    assertEquals(expected.getReps(), result.getReps());
    assertEquals(expected.getDescription(), result.getDescription());
  }

  @Test
  void getOkResultSecondQuery() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    setGroup.setSets(Collections.emptySet());
    final Set expected = easyRandom.nextObject(Set.class);

    when(setGroupDao.getSetGroupWithWorkoutById(setGroup.getId())).thenReturn(List.of(setGroup));
    doNothing().when(authWorkoutsService).checkAccess(setGroup, AuthOperations.READ);
    when(setDao.findLastSetForExerciseAndUserAux(
            setGroup.getWorkout().getUserId(),
            setGroup.getExerciseId(),
            setGroup.getWorkout().getDate()))
        .thenReturn(List.of(expected));

    final var result = getNewSetDataService.execute(setGroup.getId());
    assertEquals(expected.getWeight(), result.getWeight());
    assertEquals(expected.getRir(), result.getRir());
    assertEquals(expected.getReps(), result.getReps());
    assertEquals(expected.getDescription(), result.getDescription());
  }

  @Test
  void getNotFoundResultSecondQuery() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    setGroup.setSets(Collections.emptySet());

    when(setGroupDao.getSetGroupWithWorkoutById(setGroup.getId())).thenReturn(List.of(setGroup));
    doNothing().when(authWorkoutsService).checkAccess(setGroup, AuthOperations.READ);
    when(setDao.findLastSetForExerciseAndUserAux(
            setGroup.getWorkout().getUserId(),
            setGroup.getExerciseId(),
            setGroup.getWorkout().getDate()))
        .thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class, () -> getNewSetDataService.execute(setGroup.getId()));
    assertEquals(Set.class.getSimpleName(), exception.getClassName());
    assertEquals("exerciseId", exception.getSearchParam());
    assertEquals(setGroup.getExerciseId(), exception.getId());
  }

  @Test
  void getSetGroupNotFound() {
    final UUID setGroupId = UUID.randomUUID();

    when(setGroupDao.getSetGroupWithWorkoutById(setGroupId)).thenReturn(Collections.emptyList());

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class, () -> getNewSetDataService.execute(setGroupId));
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
        .checkAccess(Mockito.any(SetGroup.class), eq(AuthOperations.READ));

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class, () -> getNewSetDataService.execute(setGroupId));
    assertEquals(SetGroup.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(setGroupId, exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(AuthOperations.READ, exception.getAuthOperations());
  }
}
