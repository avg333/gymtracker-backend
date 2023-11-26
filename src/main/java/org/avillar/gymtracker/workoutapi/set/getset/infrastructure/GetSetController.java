package org.avillar.gymtracker.workoutapi.set.getset.infrastructure;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.SetControllerDocumentation.SetControllerTag;
import org.avillar.gymtracker.workoutapi.set.getset.infrastructure.GetSetControllerDocumentation.Methods.GetSetDocumentation;
import org.avillar.gymtracker.workoutapi.set.getset.infrastructure.model.GetSetResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@SetControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface GetSetController {

  @GetSetDocumentation
  @GetMapping("/sets/{setId}")
  @ResponseStatus(HttpStatus.OK)
  GetSetResponse execute(@PathVariable UUID setId)
      throws SetNotFoundException, WorkoutIllegalAccessException;
}
