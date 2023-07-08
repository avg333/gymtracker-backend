package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.GetExercisesByFilterService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.mapper.GetExercisesByFilterControllerMapper;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterRequest;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GetExercisesByFilterControllerImpl implements GetExercisesByFilterController {

  private final GetExercisesByFilterService getExercisesByFilterService;
  private final GetExercisesByFilterControllerMapper getExercisesByFilterControllerMapper;

  @Override
  public ResponseEntity<List<GetExercisesByFilterResponse>> execute(
      final String name,
      final String description,
      final Boolean unilateral,
      final List<UUID> loadTypeIds,
      final List<UUID> muscleSupGroupIds,
      final List<UUID> muscleGroupIds,
      final List<UUID> muscleSubGroupIds) {
    return ResponseEntity.ok(
        getExercisesByFilterControllerMapper.map(
            getExercisesByFilterService.execute(
                getExercisesByFilterControllerMapper.map(
                    GetExercisesByFilterRequest.builder()
                        .name(name)
                        .description(description)
                        .unilateral(unilateral)
                        .loadTypeIds(loadTypeIds)
                        .muscleSupGroupIds(muscleSupGroupIds)
                        .muscleGroupIds(muscleGroupIds)
                        .muscleSubGroupIds(muscleSubGroupIds)
                        .build()))));
  }
}
