package org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.application.UpdateSetGroupSetsService;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.mapper.UpdateSetGroupSetsControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.model.UpdateSetGroupSetsRequest;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.model.UpdateSetGroupSetsResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateSetGroupSetsControllerImpl implements UpdateSetGroupSetsController {

  private final UpdateSetGroupSetsService updateSetGroupSetsService;
  private final UpdateSetGroupSetsControllerMapper updateSetGroupSetsControllerMapper;

  @Override
  public List<UpdateSetGroupSetsResponse> execute(
      final UUID setGroupId, final UpdateSetGroupSetsRequest updateSetGroupSetsRequest)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    return updateSetGroupSetsControllerMapper.map(
        updateSetGroupSetsService.execute(setGroupId, updateSetGroupSetsRequest.setGroupId()));
  }
}
