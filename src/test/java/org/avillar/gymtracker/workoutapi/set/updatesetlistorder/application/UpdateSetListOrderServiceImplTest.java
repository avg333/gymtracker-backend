package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.common.sort.application.EntitySorter;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetDao;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.mapper.UpdateSetListOrderServiceMapperImpl;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.model.UpdateSetListOrderResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateSetListOrderServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private UpdateSetListOrderServiceImpl updateSetListOrderService;

  @Mock private SetDao setDao;
  @Spy private EntitySorter entitySorter;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private UpdateSetListOrderServiceMapperImpl updateSetListOrderServiceMapper;

  @Test
  void updateOk() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    final Set setFirst = new Set(null, 1, 1.0, 1.0, setGroup);
    setFirst.setListOrder(0);
    setFirst.setId(UUID.randomUUID());
    final Set setSecond = new Set(null, 1, 1.0, 1.0, setGroup);
    setSecond.setListOrder(1);
    setSecond.setId(UUID.randomUUID());
    setGroup.setSets(new HashSet<>(java.util.Set.of(setFirst, setSecond)));
    final List<Set> sets = new ArrayList<>(setGroup.getSets());

    when(setDao.getSetFullById(setFirst.getId())).thenReturn(List.of(setFirst));
    when(setDao.getSetsBySetGroupId(setGroup.getId())).thenReturn(sets);
    doNothing().when(authWorkoutsService).checkAccess(setFirst, AuthOperations.UPDATE);

    final List<UpdateSetListOrderResponseApplication> result =
        updateSetListOrderService.execute(setFirst.getId(), 1);
    //    assertEquals(setGroup.getSets().size(), result.size()); FIXME
    //    assertEquals(setFirst.getId(), result.get(0).getId());
    //     assertEquals(setFirst.getListOrder(), result.get(0).getListOrder());
    //     assertEquals(setFirst.getReps(), result.get(0).getReps());
    //     assertEquals(setFirst.getRir(), result.get(0).getRir());
    //     assertEquals(setFirst.getWeight(), result.get(0).getWeight());
    //     assertEquals(setFirst.getDescription(), result.get(0).getDescription());
    //    assertEquals(setGroup.getId(), result.get(0).getSetGroup().getId());
    //     assertEquals(setSecond.getId(), result.get(1).getId());
    //     assertEquals(setSecond.getListOrder(), result.get(1).getListOrder());
    //     assertEquals(setSecond.getReps(), result.get(1).getReps());
    //     assertEquals(setSecond.getRir(), result.get(1).getRir());
    //     assertEquals(setSecond.getWeight(), result.get(1).getWeight());
    //     assertEquals(setSecond.getReps(), result.get(1).getReps());
    //     assertEquals(setSecond.getListOrder(), result.get(1).getListOrder());
    //     assertEquals(setSecond.getListOrder(), result.get(1).getListOrder());
    //     assertEquals(setSecond.getDescription(), result.get(1).getDescription());
    //     assertEquals(setGroup.getId(), result.get(1).getSetGroup().getId());
    verify(entitySorter).sortUpdate(sets, setFirst, 0);
    verify(setDao).saveAll(sets);
  }

  @Test
  void updateSameValue() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    final List<Set> sets = easyRandom.objects(Set.class, 5).toList();
    sets.forEach(set -> set.setSetGroup(setGroup));
    sets.get(0).setListOrder(1);

    when(setDao.getSetFullById(sets.get(0).getId())).thenReturn(List.of(sets.get(0)));
    when(setDao.getSetsBySetGroupId(setGroup.getId())).thenReturn(new ArrayList<>(sets));
    doNothing().when(authWorkoutsService).checkAccess(sets.get(0), AuthOperations.UPDATE);

    final List<UpdateSetListOrderResponseApplication> result =
        updateSetListOrderService.execute(sets.get(0).getId(), sets.get(0).getListOrder());
    assertEquals(sets.size(), result.size());
    for (int i = 0; i < sets.size(); i++) {
      final var setExpected = sets.get(i);
      final var setResult = result.get(i);
      assertEquals(setExpected.getId(), setResult.getId());
      assertEquals(setExpected.getListOrder(), setResult.getListOrder());
      assertEquals(setExpected.getRir(), setResult.getRir());
      assertEquals(setExpected.getWeight(), setResult.getWeight());
      assertEquals(setExpected.getReps(), setResult.getReps());
      assertEquals(setExpected.getDescription(), setResult.getDescription());
      assertEquals(setExpected.getCompletedAt(), setResult.getCompletedAt());
      assertEquals(setExpected.getSetGroup().getId(), setResult.getSetGroup().getId());
    }
    verify(entitySorter, never()).sortUpdate(any(), any(), anyInt());
    verify(setDao, never()).saveAll(any());
  }

  @Test
  void updateNotFound() {
    final UUID setId = UUID.randomUUID();

    final EntityNotFoundException exception =
        assertThrows(
            EntityNotFoundException.class, () -> updateSetListOrderService.execute(setId, 1));
    assertEquals(Set.class.getSimpleName(), exception.getClassName());
    assertEquals(setId, exception.getId());
    verify(entitySorter, never()).sortUpdate(any(), any(), anyInt());
    verify(setDao, never()).saveAll(any());
  }

  @Test
  void updateNotPermission() {
    final UUID userId = UUID.randomUUID();
    final Set set = easyRandom.nextObject(Set.class);
    final AuthOperations updateOperation = AuthOperations.UPDATE;

    when(setDao.getSetFullById(set.getId())).thenReturn(List.of(set));
    doThrow(new IllegalAccessException(set, updateOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(set, updateOperation);

    final IllegalAccessException exception =
        assertThrows(
            IllegalAccessException.class, () -> updateSetListOrderService.execute(set.getId(), 1));
    assertEquals(Set.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(set.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(updateOperation, exception.getAuthOperations());
    verify(entitySorter, never()).sortUpdate(any(), any(), anyInt());
    verify(setDao, never()).saveAll(any());
  }
}
