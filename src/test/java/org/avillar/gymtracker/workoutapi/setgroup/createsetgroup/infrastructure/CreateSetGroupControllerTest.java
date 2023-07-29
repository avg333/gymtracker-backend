package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.CreateSetGroupService;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.mapper.CreateSetGroupControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupRequest;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupResponse;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class CreateSetGroupControllerTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private CreateSetGroupControllerImpl createSetGroupController;

  @Mock private CreateSetGroupService createSetGroupService;
  @Spy private CreateSetGroupControllerMapperImpl postSetGroupControllerMapper;

  @Test
  void post() {
    final CreateSetGroupResponseApplication expected =
        easyRandom.nextObject(CreateSetGroupResponseApplication.class);
    final CreateSetGroupRequest request = new CreateSetGroupRequest();
    request.setExerciseId(expected.getExerciseId());
    request.setDescription(expected.getDescription());

    when(createSetGroupService.execute(
            expected.getWorkout().getId(), postSetGroupControllerMapper.map(request)))
        .thenReturn(expected);

    final ResponseEntity<CreateSetGroupResponse> result =
        createSetGroupController.execute(expected.getWorkout().getId(), request);
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isNotNull();
    assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
  }
}
