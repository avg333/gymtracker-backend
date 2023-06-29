package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.UpdateSetListOrderService;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.mapper.UpdateSetListOrderControllerMapper;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderResponseInfrastructure;
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
  public ResponseEntity<UpdateSetListOrderResponseInfrastructure> patch(
      @PathVariable final UUID setId,
      @RequestBody
          final UpdateSetListOrderRequestInfrastructure updateSetListOrderRequestInfrastructure) {
    return ResponseEntity.ok(
        updateSetListOrderControllerMapper.map(
            updateSetListOrderService.execute(
                setId, updateSetListOrderRequestInfrastructure.getListOrder())));
  }
}
