package org.avillar.gymtracker.workoutapi.set.createwarmupsets.infrastructure;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.SetControllerDocumentation.SetControllerTag;
import org.avillar.gymtracker.workoutapi.set.createwarmupsets.infrastructure.CreateWarmupSetsControllerDocumentation.Methods.CreateWarmupSetsDocumentation;
import org.avillar.gymtracker.workoutapi.set.createwarmupsets.infrastructure.model.CreateWarmupSetsRequest;
import org.avillar.gymtracker.workoutapi.set.createwarmupsets.infrastructure.model.CreateWarmupSetsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@SetControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface CreateWarmupSetsController {

  @CreateWarmupSetsDocumentation
  @PostMapping("/setGroups/{setGroupId}/setGroups/warmup")
  @ResponseStatus(HttpStatus.OK)
  List<CreateWarmupSetsResponse> execute(
      @PathVariable UUID setGroupId, CreateWarmupSetsRequest createWarmupSetsRequest)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException;
}
