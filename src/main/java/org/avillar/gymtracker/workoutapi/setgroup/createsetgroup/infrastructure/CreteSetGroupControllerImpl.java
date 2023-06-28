package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.CreateSetGroupService;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.mapper.CreateSetGroupControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreteSetGroupControllerImpl implements CreteSetGroupController {

  private final CreateSetGroupService createSetGroupService;
  private final CreateSetGroupControllerMapper createSetGroupControllerMapper;

  @Override
  public ResponseEntity<CreateSetGroupResponseInfrastructure> execute(
      final UUID workoutId,
      final CreateSetGroupRequestInfrastructure createSetGroupRequestInfrastructure) {
    return ResponseEntity.ok(
        createSetGroupControllerMapper.map(
            createSetGroupService.execute(
                workoutId,
                createSetGroupControllerMapper.map(createSetGroupRequestInfrastructure))));
  }
}
