package org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.getexercisebyfilters;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisesbyfilter.GetExercisesByFilterService;
import org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.getexercisebyfilters.mapper.GetExercisesByFilterControllerMapper;
import org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.getexercisebyfilters.model.GetExerciseInfrastructureRequest;
import org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.getexercisebyfilters.model.GetExerciseInfrastructureResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "${exercisesApiPrefix}")
@RequiredArgsConstructor
public class GetExercisesByFilterController {

  private final GetExercisesByFilterService getExercisesByFilterService;
  private final GetExercisesByFilterControllerMapper getExercisesByFilterControllerMapper;

  @GetMapping("exercises")
  public ResponseEntity<List<GetExerciseInfrastructureResponse>> getAllExercises(
      @RequestParam(required = false) final String name,
      @RequestParam(required = false) final String description,
      @RequestParam(required = false) final Boolean unilateral,
      @RequestParam(required = false) final UUID loadTypeId,
      @RequestParam(required = false) final List<UUID> muscleSupGroupIds,
      @RequestParam(required = false) final UUID muscleGroupId,
      @RequestParam(required = false) final List<UUID> muscleSubGroupIds) {
    return ResponseEntity.ok(
        getExercisesByFilterControllerMapper.map(
            getExercisesByFilterService.execute(
                getExercisesByFilterControllerMapper.map(
                    GetExerciseInfrastructureRequest.builder()
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
