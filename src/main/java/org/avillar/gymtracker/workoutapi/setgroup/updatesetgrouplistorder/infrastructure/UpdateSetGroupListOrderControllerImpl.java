package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.common.exception.application.ListOrderNotValidException;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application.UpdateSetGroupListOrderService;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.mapper.UpdateSetGroupListOrderControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model.UpdateSetGroupListOrderRequest;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model.UpdateSetGroupListOrderResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateSetGroupListOrderControllerImpl implements UpdateSetGroupListOrderController {

  private final UpdateSetGroupListOrderService updateSetGroupListOrderService;
  private final UpdateSetGroupListOrderControllerMapper updateSetGroupListOrderControllerMapper;

  @Override
  public List<UpdateSetGroupListOrderResponse> execute(
      final UUID setGroupId, final UpdateSetGroupListOrderRequest updateSetGroupListOrderRequest)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException, ListOrderNotValidException {
    return updateSetGroupListOrderControllerMapper.map(
        updateSetGroupListOrderService.execute(
            setGroupId, updateSetGroupListOrderRequest.listOrder()));
  }
}
