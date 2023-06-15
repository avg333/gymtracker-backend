package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.listorder;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.listorder.UpdateSetGroupListOrderService;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.listorder.mapper.UpdateSetGroupListOrderControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.listorder.model.UpdateSetGroupListOrderRequest;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.listorder.model.UpdateSetGroupListOrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class UpdateSetGroupListOrderController {

  private final UpdateSetGroupListOrderService updateSetGroupListOrderService;
  private final UpdateSetGroupListOrderControllerMapper updateSetGroupListOrderControllerMapper;

  @PatchMapping("/setGroups/{setGroupId}/listOrder")
  public ResponseEntity<UpdateSetGroupListOrderResponse> updateSetGroupExercise(
      @PathVariable final UUID setGroupId,
      @Valid @RequestBody final UpdateSetGroupListOrderRequest updateSetGroupExerciseRequest) {
    return ResponseEntity.ok(
        updateSetGroupListOrderControllerMapper.updateResponse(
            updateSetGroupListOrderService.update(
                setGroupId, updateSetGroupExerciseRequest.getListOrder())));
  }
}