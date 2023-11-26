package org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.SetGroupControllerDocumentation.SetGroupControllerTag;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.model.UpdateSetGroupSetsRequest;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.model.UpdateSetGroupSetsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@SetGroupControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface UpdateSetGroupSetsController {

  @PatchMapping("/setGroups/{setGroupId}/sets")
  @ResponseStatus(HttpStatus.OK)
  List<UpdateSetGroupSetsResponse> execute(
      @PathVariable UUID setGroupId,
      @Valid @RequestBody UpdateSetGroupSetsRequest updateSetGroupSetsRequest)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException;
}
