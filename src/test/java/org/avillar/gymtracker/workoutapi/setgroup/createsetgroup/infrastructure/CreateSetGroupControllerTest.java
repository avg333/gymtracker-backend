package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.CreateSetGroupService;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupRequestApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.mapper.CreateSetGroupControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupRequest;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class CreateSetGroupControllerTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private CreateSetGroupControllerImpl createSetGroupController;

  @Mock private CreateSetGroupService createSetGroupService;

  @Spy
  private final CreateSetGroupControllerMapper postSetGroupControllerMapper =
      Mappers.getMapper(CreateSetGroupControllerMapper.class);

  @Test
  void post() {
    final CreateSetGroupResponseApplication expected =
        easyRandom.nextObject(CreateSetGroupResponseApplication.class);
    final CreateSetGroupRequest request = new CreateSetGroupRequest();
    request.setExerciseId(expected.getExerciseId());
    request.setDescription(expected.getDescription());

    final ArgumentCaptor<CreateSetGroupRequestApplication> createSetGroupRequestApplicationCaptor =
        ArgumentCaptor.forClass(CreateSetGroupRequestApplication.class);

    when(createSetGroupService.execute(
            eq(expected.getWorkout().getId()), createSetGroupRequestApplicationCaptor.capture()))
        .thenReturn(expected);

    assertThat(createSetGroupController.execute(expected.getWorkout().getId(), request))
        .usingRecursiveComparison()
        .isEqualTo(expected);
    assertThat(createSetGroupRequestApplicationCaptor.getValue())
        .usingRecursiveComparison()
        .isEqualTo(request);
  }
}
