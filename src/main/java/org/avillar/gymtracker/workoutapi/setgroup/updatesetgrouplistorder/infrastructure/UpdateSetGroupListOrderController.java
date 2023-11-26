package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.ListOrderNotValidException;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.SetGroupControllerDocumentation.SetGroupControllerTag;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.UpdateSetGroupListOrderControllerDocumentation.Methods.UpdateSetGroupListOrderDocumentation;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model.UpdateSetGroupListOrderRequest;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model.UpdateSetGroupListOrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@SetGroupControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface UpdateSetGroupListOrderController {

  @UpdateSetGroupListOrderDocumentation
  @PatchMapping("/setGroups/{setGroupId}/listOrder")
  @ResponseStatus(HttpStatus.OK)
  List<UpdateSetGroupListOrderResponse> execute(
      @PathVariable UUID setGroupId,
      @Valid @RequestBody UpdateSetGroupListOrderRequest updateSetGroupListOrderRequest)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException, ListOrderNotValidException;
}
