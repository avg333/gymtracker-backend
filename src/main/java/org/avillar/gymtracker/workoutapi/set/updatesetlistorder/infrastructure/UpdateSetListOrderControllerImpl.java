package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.common.exception.application.ListOrderNotValidException;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.UpdateSetListOrderService;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.mapper.UpdateSetListOrderControllerMapper;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderRequest;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateSetListOrderControllerImpl implements UpdateSetListOrderController {

  private final UpdateSetListOrderService updateSetListOrderService;
  private final UpdateSetListOrderControllerMapper updateSetListOrderControllerMapper;

  @Override
  public List<UpdateSetListOrderResponse> execute(
      final UUID setId, final UpdateSetListOrderRequest updateSetListOrderRequest)
      throws SetNotFoundException, WorkoutIllegalAccessException, ListOrderNotValidException {
    return updateSetListOrderControllerMapper.map(
        updateSetListOrderService.execute(setId, updateSetListOrderRequest.listOrder()));
  }
}
