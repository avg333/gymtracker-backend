package org.avillar.gymtracker.workoutapi.set.createwarmupsets.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.facade.setgroup.SetGroupFacade;
import org.avillar.gymtracker.workoutapi.set.createwarmupsets.application.CreateWarmupSetsServiceImpl.Exhaustiveness;
import org.instancio.Instancio;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class CreateWarmupSetsServiceImplTest {

  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.UPDATE;

  @InjectMocks private CreateWarmupSetsServiceImpl createWarmupSetsServiceImpl;

  @Mock private SetGroupFacade setGroupFacade;
  @Mock private AuthWorkoutsService authWorkoutsService;

  @ParameterizedTest
  @EnumSource(Exhaustiveness.class)
  void shouldExecuteCreateWarmupSetsSuccessfully(final Exhaustiveness exhaustiveness)
      throws WorkoutIllegalAccessException, SetGroupNotFoundException {
    final UUID setGroupId = UUID.randomUUID();
    final Set set = Instancio.create(Set.class);
    final SetGroup setGroup = Instancio.create(SetGroup.class);

    when(setGroupFacade.getSetGroupFull(setGroupId)).thenReturn(setGroup);
    doNothing().when(authWorkoutsService).checkAccess(setGroup, AUTH_OPERATIONS);

    assertThat(createWarmupSetsServiceImpl.execute(setGroupId, set, exhaustiveness)).isNotNull();
  }
}
