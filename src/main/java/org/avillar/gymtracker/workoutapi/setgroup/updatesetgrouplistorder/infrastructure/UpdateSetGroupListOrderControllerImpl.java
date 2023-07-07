package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application.UpdateSetGroupListOrderService;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.mapper.UpdateSetGroupListOrderControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model.UpdateSetGroupListOrderRequest;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model.UpdateSetGroupListOrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateSetGroupListOrderControllerImpl implements UpdateSetGroupListOrderController {

  private final UpdateSetGroupListOrderService updateSetGroupListOrderService;
  private final UpdateSetGroupListOrderControllerMapper updateSetGroupListOrderControllerMapper;

  @Override
  public ResponseEntity<List<UpdateSetGroupListOrderResponse>> execute(
      final UUID setGroupId, final UpdateSetGroupListOrderRequest updateSetGroupListOrderRequest)
      throws EntityNotFoundException, IllegalAccessException {
    return ResponseEntity.ok(
        updateSetGroupListOrderControllerMapper.map(
            updateSetGroupListOrderService.execute(
                setGroupId, updateSetGroupListOrderRequest.getListOrder())));
  }
}
