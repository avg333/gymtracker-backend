package org.avillar.gymtracker.workoutapi.set.infrastructure.update.listorder;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.set.application.update.listorder.UpdateSetListOrderService;
import org.avillar.gymtracker.workoutapi.set.infrastructure.update.listorder.mapper.UpdateSetListOrderControllerMapper;
import org.avillar.gymtracker.workoutapi.set.infrastructure.update.listorder.model.UpdateSetListOrderRequest;
import org.avillar.gymtracker.workoutapi.set.infrastructure.update.listorder.model.UpdateSetListOrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class UpdateSetListOrderController {

  private final UpdateSetListOrderService updateSetListOrderService;
  private final UpdateSetListOrderControllerMapper updateSetListOrderControllerMapper;

  @PatchMapping("sets/{setId}/listOrder")
  public ResponseEntity<UpdateSetListOrderResponse> updateSetListOrder(
      @PathVariable final UUID setId,
      @RequestBody final UpdateSetListOrderRequest updateSetListOrderRequest) {
    return ResponseEntity.ok(
        updateSetListOrderControllerMapper.updateResponse(
            updateSetListOrderService.updateSetListOrder(
                setId, updateSetListOrderRequest.getListOrder())));
  }
}
