package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure;

import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.SetGroupControllerDocumentation.SetGroupControllerTag;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure.UpdateSetGroupDescriptionControllerDocumentation.Methods.UpdateSetGroupDescriptionDocumentation;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure.model.UpdateSetGroupDescriptionRequest;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure.model.UpdateSetGroupDescriptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@SetGroupControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface UpdateSetGroupDescriptionController {

  @UpdateSetGroupDescriptionDocumentation
  @PatchMapping("/setGroups/{setGroupId}/description")
  @ResponseStatus(HttpStatus.OK)
  UpdateSetGroupDescriptionResponse execute(
      @PathVariable UUID setGroupId,
      @Valid @RequestBody UpdateSetGroupDescriptionRequest updateSetGroupDescriptionRequest)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException;
}
