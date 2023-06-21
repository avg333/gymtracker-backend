package org.avillar.gymtracker.workoutapi.set.application.update.listorder;

import static org.junit.jupiter.api.Assertions.*;

import org.avillar.gymtracker.common.sort.application.EntitySorter;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.set.application.update.listorder.mapper.UpdateSetListOrderServiceMapper;
import org.avillar.gymtracker.workoutapi.set.domain.SetDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateSetListOrderServiceImplTest {
  private UpdateSetListOrderService updateSetListOrderService;

  @Mock private SetDao setDao;
  @Mock private EntitySorter entitySorter;
  @Mock private AuthWorkoutsService authWorkoutsService;
  @Spy private UpdateSetListOrderServiceMapper updateSetListOrderServiceMapper;

  @BeforeEach
  void beforeEach() {
    updateSetListOrderService =
        new UpdateSetListOrderServiceImpl(
            setDao, entitySorter, authWorkoutsService, updateSetListOrderServiceMapper);
  }

  @Test
  void updateOk() {
    // TODO Completar esto
  }
}
