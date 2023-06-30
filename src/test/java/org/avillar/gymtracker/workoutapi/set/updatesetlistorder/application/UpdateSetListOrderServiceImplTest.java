package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    Mockito.doNothing().when(authWorkoutsService).checkAccess(setFirst, AuthOperations.UPDATE);

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
    final Set setFirst = new Set(null, 1, 1.0, 1.0, setGroup);
    setFirst.setListOrder(0);
    setFirst.setId(UUID.randomUUID());
    final Set setSecond = new Set(null, 1, 1.0, 1.0, setGroup);
    setSecond.setListOrder(1);
    setSecond.setId(UUID.randomUUID());
    setGroup.setSets(java.util.Set.of(setFirst, setSecond));
    final List<Set> sets = setGroup.getSets().stream().toList();

    when(setDao.getSetFullById(setFirst.getId())).thenReturn(List.of(setFirst));
    when(setDao.getSetsBySetGroupId(setGroup.getId())).thenReturn(sets);
    Mockito.doNothing().when(authWorkoutsService).checkAccess(setFirst, AuthOperations.UPDATE);

    final List<UpdateSetListOrderResponseApplication> result =
        updateSetListOrderService.execute(setFirst.getId(), 0);
    assertEquals(setGroup.getSets().size(), result.size());
    assertEquals(setFirst.getId(), result.get(0).getId());
    assertEquals(setFirst.getListOrder(), result.get(0).getListOrder());
    assertEquals(setFirst.getReps(), result.get(0).getReps());
    assertEquals(setFirst.getRir(), result.get(0).getRir());
    assertEquals(setFirst.getWeight(), result.get(0).getWeight());
    assertEquals(setFirst.getDescription(), result.get(0).getDescription());
    assertEquals(setGroup.getId(), result.get(0).getSetGroup().getId());
    assertEquals(setSecond.getId(), result.get(1).getId());
    assertEquals(setSecond.getListOrder(), result.get(1).getListOrder());
    assertEquals(setSecond.getReps(), result.get(1).getReps());
    assertEquals(setSecond.getRir(), result.get(1).getRir());
    assertEquals(setSecond.getWeight(), result.get(1).getWeight());
    assertEquals(setSecond.getReps(), result.get(1).getReps());
    assertEquals(setSecond.getListOrder(), result.get(1).getListOrder());
    assertEquals(setSecond.getListOrder(), result.get(1).getListOrder());
    assertEquals(setSecond.getDescription(), result.get(1).getDescription());
    assertEquals(setGroup.getId(), result.get(1).getSetGroup().getId());
    verify(entitySorter, never()).sortUpdate(any(), any(), anyInt());
    verify(setDao, never()).saveAll(any());
  }

  @Test
  void updateNotFound() {
    final UUID setId = UUID.randomUUID();
    final int listOrder = 1;

    final EntityNotFoundException exception =
        Assertions.assertThrows(
            EntityNotFoundException.class,
            () -> updateSetListOrderService.execute(setId, listOrder));
    assertEquals(Set.class.getSimpleName(), exception.getClassName());
    assertEquals(setId, exception.getId());
    verify(entitySorter, never()).sortUpdate(any(), any(), anyInt());
    verify(setDao, never()).saveAll(any());
  }

  @Test
  void updateNotPermission() {
    final UUID userId = UUID.randomUUID();
    final Set set = easyRandom.nextObject(Set.class);
    final int listOrder = 1;
    final AuthOperations authOperation = AuthOperations.UPDATE;

    when(setDao.getSetFullById(set.getId())).thenReturn(List.of(set));
    doThrow(new IllegalAccessException(set, authOperation, userId))
        .when(authWorkoutsService)
        .checkAccess(set, authOperation);

    final IllegalAccessException exception =
        Assertions.assertThrows(
            IllegalAccessException.class,
            () -> updateSetListOrderService.execute(set.getId(), listOrder));
    assertEquals(Set.class.getSimpleName(), exception.getEntityClassName());
    assertEquals(set.getId(), exception.getEntityId());
    assertEquals(userId, exception.getCurrentUserId());
    assertEquals(authOperation, exception.getAuthOperations());
    verify(entitySorter, never()).sortUpdate(any(), any(), anyInt());
    verify(setDao, never()).saveAll(any());
  }
}
