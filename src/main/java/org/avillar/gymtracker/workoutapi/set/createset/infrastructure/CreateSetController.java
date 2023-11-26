package org.avillar.gymtracker.workoutapi.set.createset.infrastructure;

import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.SetControllerDocumentation.SetControllerTag;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.CreateSetControllerDocumentation.Methods.CreateSetDocumentation;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetRequest;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@SetControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface CreateSetController {

  @CreateSetDocumentation
  @PostMapping("/setGroups/{setGroupId}/sets")
  @ResponseStatus(HttpStatus.OK)
  CreateSetResponse execute(
      @PathVariable UUID setGroupId, @Valid @RequestBody CreateSetRequest createSetRequest)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException;
}
