package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.UpdateSetListOrderService;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.mapper.UpdateSetListOrderControllerMapper;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderRequest;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateSetListOrderControllerImpl implements UpdateSetListOrderController {

  private final UpdateSetListOrderService updateSetListOrderService;
  private final UpdateSetListOrderControllerMapper updateSetListOrderControllerMapper;

  @Override
  public ResponseEntity<List<UpdateSetListOrderResponse>> execute(
      final UUID setId, final UpdateSetListOrderRequest updateSetListOrderRequest)
      throws EntityNotFoundException, IllegalAccessException {
    return ResponseEntity.ok(
        updateSetListOrderControllerMapper.map(
            updateSetListOrderService.execute(setId, updateSetListOrderRequest.getListOrder())));
  }
}
