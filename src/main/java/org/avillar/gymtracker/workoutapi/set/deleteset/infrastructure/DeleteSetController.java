package org.avillar.gymtracker.workoutapi.set.deleteset.infrastructure;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.SetControllerDocumentation.SetControllerTag;
import org.avillar.gymtracker.workoutapi.set.deleteset.infrastructure.DeleteSetControllerDocumentation.Methods.DeleteSetDocumentation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@SetControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface DeleteSetController {

  @DeleteSetDocumentation
  @DeleteMapping("/sets/{setId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  Void execute(@PathVariable UUID setId) throws SetNotFoundException, WorkoutIllegalAccessException;
}
