package org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.SetControllerDocumentation.SetControllerTag;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure.GetNewSetDataControllerDocumentation.Methods.GetNewSetDataDocumentation;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure.model.GetNewSetDataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@SetControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface GetNewSetDataController {

  @GetNewSetDataDocumentation
  @GetMapping("/setGroups/{setGroupId}/sets/newSet")
  @ResponseStatus(HttpStatus.OK)
  GetNewSetDataResponse execute(@PathVariable UUID setGroupId)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException;
}
