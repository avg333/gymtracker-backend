package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.CreateSetGroupService;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.mapper.CreateSetGroupControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupRequest;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreateSetGroupControllerImpl implements CreateSetGroupController {

  private final CreateSetGroupService createSetGroupService;
  private final CreateSetGroupControllerMapper createSetGroupControllerMapper;

  @Override
  public ResponseEntity<CreateSetGroupResponse> execute(
      final UUID workoutId, final CreateSetGroupRequest createSetGroupRequest)
      throws EntityNotFoundException, IllegalAccessException, ExerciseNotFoundException {
    return ResponseEntity.ok(
        createSetGroupControllerMapper.map(
            createSetGroupService.execute(
                workoutId, createSetGroupControllerMapper.map(createSetGroupRequest))));
  }
}
