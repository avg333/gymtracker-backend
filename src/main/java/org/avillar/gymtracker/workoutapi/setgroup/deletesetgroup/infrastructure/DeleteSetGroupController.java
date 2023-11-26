package org.avillar.gymtracker.workoutapi.setgroup.deletesetgroup.infrastructure;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.SetGroupControllerDocumentation.SetGroupControllerTag;
import org.avillar.gymtracker.workoutapi.setgroup.deletesetgroup.infrastructure.DeleteSetGroupControllerDocumentation.Methods.DeleteSetGroupDocumentation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@SetGroupControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface DeleteSetGroupController {

  @DeleteSetGroupDocumentation
  @DeleteMapping("/setGroups/{setGroupId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  Void execute(@PathVariable UUID setGroupId)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException;
}
