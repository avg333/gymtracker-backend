package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.SetGroupControllerDocumentation.SetGroupControllerTag;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.GetSetGroupControllerDocumentation.Methods.GetSetGroupDocumentation;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.model.GetSetGroupResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@SetGroupControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface GetSetGroupController {

  @GetSetGroupDocumentation
  @GetMapping("/setGroups/{setGroupId}")
  @ResponseStatus(HttpStatus.OK)
  GetSetGroupResponse execute(@PathVariable UUID setGroupId)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException;
}
