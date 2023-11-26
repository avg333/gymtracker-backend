package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.ListOrderNotValidException;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.SetControllerDocumentation.SetControllerTag;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.UpdateSetListOrderControllerDocumentation.Methods.UpdateSetListOrderDocumentation;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderRequest;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@SetControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface UpdateSetListOrderController {

  @UpdateSetListOrderDocumentation
  @PatchMapping("/sets/{setId}/listOrder")
  @ResponseStatus(HttpStatus.OK)
  List<UpdateSetListOrderResponse> execute(
      @PathVariable UUID setId,
      @Valid @RequestBody UpdateSetListOrderRequest updateSetListOrderRequest)
      throws SetNotFoundException, WorkoutIllegalAccessException, ListOrderNotValidException;
}
