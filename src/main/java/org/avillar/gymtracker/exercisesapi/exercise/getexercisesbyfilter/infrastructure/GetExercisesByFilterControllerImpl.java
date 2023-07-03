package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.GetExercisesByFilterService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.mapper.GetExercisesByFilterControllerMapper;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterRequestInfrastructure;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GetExercisesByFilterControllerImpl implements GetExercisesByFilterController {

  private final GetExercisesByFilterService getExercisesByFilterService;
  private final GetExercisesByFilterControllerMapper getExercisesByFilterControllerMapper;

  @Override
  public ResponseEntity<List<GetExercisesByFilterResponseInfrastructure>> execute(
      final String name,
      final String description,
      final Boolean unilateral,
      final UUID loadTypeId,
      final List<UUID> muscleSupGroupIds,
      final UUID muscleGroupId,
      final List<UUID> muscleSubGroupIds) {
    return ResponseEntity.ok(
        getExercisesByFilterControllerMapper.map(
            getExercisesByFilterService.execute(
                getExercisesByFilterControllerMapper.map(
                    GetExercisesByFilterRequestInfrastructure.builder()
                        .name(name)
                        .description(description)
                        .unilateral(unilateral)
                        .loadTypeId(loadTypeId)
                        .muscleSupGroupIds(muscleSupGroupIds)
                        .muscleGroupId(muscleGroupId)
                        .muscleSubGroupIds(muscleSubGroupIds)
                        .build()))));
  }
}
