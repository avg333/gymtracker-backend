package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.mapper.UpdateSetListOrderServiceMapper;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.model.UpdateSetListOrderResponseApplication;
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
class UpdateSetListOrderServiceImplTest {

  private static final int LIST_SIZE = 5;

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private UpdateSetListOrderServiceImpl updateSetListOrderService;

  @Mock private SetDao setDao;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private EntitySorter entitySorter;

  @Spy
  private final UpdateSetListOrderServiceMapper updateSetListOrderServiceMapper =
      Mappers.getMapper(UpdateSetListOrderServiceMapper.class);

  @Test
  void updateOk() {
    final List<Set> sets = getSets();
    final Set setFirst = sets.get(0);
    final int newPosition = 3;

    when(setDao.getSetFullById(setFirst.getId())).thenReturn(List.of(setFirst));
    when(setDao.getSetsBySetGroupId(setFirst.getSetGroup().getId()))
        .thenReturn(new ArrayList<>(sets));
    doNothing().when(authWorkoutsService).checkAccess(setFirst, AuthOperations.UPDATE);

    final List<UpdateSetListOrderResponseApplication> result =
        updateSetListOrderService.execute(setFirst.getId(), newPosition);
    //    assertThat(result).hasSameSizeAs(sets);// FIXME
    //    assertThat(result)
    //        .usingRecursiveComparison()
    //        .ignoringFields("listOrder")
    //        .isEqualTo(sets); // FIXME
    //    verify(entitySorter).sortUpdate(sets, setFirst, 0);
    //    verify(setDao).saveAll(sets);
  }

  @Test
  void updateSameValue() {
    final List<Set> sets = getSets();
    final Set setLast = sets.get(sets.size() - 1);

    when(setDao.getSetFullById(setLast.getId())).thenReturn(List.of(setLast));
    when(setDao.getSetsBySetGroupId(setLast.getSetGroup().getId()))
        .thenReturn(new ArrayList<>(sets));
    doNothing().when(authWorkoutsService).checkAccess(setLast, AuthOperations.UPDATE);

    final List<UpdateSetListOrderResponseApplication> result =
        updateSetListOrderService.execute(setLast.getId(), setLast.getListOrder());
    assertThat(result).hasSameSizeAs(sets);
    assertThat(result).usingRecursiveComparison().isEqualTo(sets);
    verify(entitySorter, never()).sortUpdate(any(), any(), anyInt());
    verify(setDao, never()).saveAll(any());
  }

  private List<Set> getSets() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    final List<Set> sets = easyRandom.objects(Set.class, LIST_SIZE).toList();
    for (int i = 0; i < sets.size(); i++) {
      final Set set = sets.get(i);
      set.setListOrder(i);
      set.setSetGroup(setGroup);
    }
    return sets;
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
