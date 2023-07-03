package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.GetSetGroupsByExerciseService;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.mapper.GetSetGroupsByExerciseControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.model.GetSetGroupsByExerciseResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetSetGroupsByExerciseControllerImpl implements GetSetGroupsByExerciseController {

  private final GetSetGroupsByExerciseService getSetGroupsByExerciseService;
  private final GetSetGroupsByExerciseControllerMapper getSetGroupsByExerciseControllerMapper;

  @Override
  public ResponseEntity<List<GetSetGroupsByExerciseResponseInfrastructure>> execute(
      final UUID userId, final UUID exerciseId)
      throws EntityNotFoundException, IllegalAccessException {
    return ResponseEntity.ok(
        getSetGroupsByExerciseControllerMapper.map(
            getSetGroupsByExerciseService.execute(userId, exerciseId)));
  }
}
