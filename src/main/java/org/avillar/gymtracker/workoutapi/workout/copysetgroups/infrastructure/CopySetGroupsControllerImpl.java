package org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.CopySetGroupsService;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.mapper.CopySetGroupsControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsRequest;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CopySetGroupsControllerImpl implements CopySetGroupsController {

  private final CopySetGroupsService copySetGroupsService;
  private final CopySetGroupsControllerMapper copySetGroupsControllerMapper;

  @Override
  public List<CopySetGroupsResponse> execute(
      final UUID workoutId, final CopySetGroupsRequest copySetGroupsRequest)
      throws EntityNotFoundException, IllegalAccessException {
    return copySetGroupsControllerMapper.map(
        copySetGroupsService.execute(
            workoutId,
            copySetGroupsRequest.getId(),
            copySetGroupsRequest.getSource() == CopySetGroupsRequest.Source.WORKOUT));
  }
}
