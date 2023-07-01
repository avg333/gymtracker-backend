package org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.CopySetGroupsService;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsResponseInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.mapper.CopySetGroupsControllerMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CopySetGroupsControllerImpl implements CopySetGroupsController {

  private final CopySetGroupsService copySetGroupsService;
  private final CopySetGroupsControllerMapper copySetGroupsControllerMapper;

  @Override
  public ResponseEntity<List<CopySetGroupsResponseInfrastructure>> execute(
      final UUID workoutId,
      final CopySetGroupsRequestInfrastructure copySetGroupsRequestInfrastructure)
      throws EntityNotFoundException, IllegalAccessException {
    return ResponseEntity.ok(
        copySetGroupsControllerMapper.map(
            copySetGroupsService.execute(
                workoutId,
                copySetGroupsRequestInfrastructure.getId(),
                copySetGroupsRequestInfrastructure.getSource()
                    == CopySetGroupsRequestInfrastructure.Source.WORKOUT)));
  }
}