package org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.GetExerciseService;
import org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.mapper.GetExerciseControllerMapper;
import org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.model.GetExerciseRequest;
import org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.model.GetExerciseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "${exercisesApiPrefix}")
@RequiredArgsConstructor
public class GetExerciseController {

  private final GetExerciseService getExerciseService;
  private final GetExerciseControllerMapper getExerciseControllerMapper;

  @GetMapping("exercises/{exerciseId}")
  public ResponseEntity<GetExerciseResponse> getExerciseById(@PathVariable final UUID exerciseId) {
    return ResponseEntity.ok(
        getExerciseControllerMapper.getResponse(getExerciseService.getById(exerciseId)));
  }

  @GetMapping("exercises")
  public ResponseEntity<List<GetExerciseResponse>> getAllExercises(
      @RequestParam(required = false) final String name,
      @RequestParam(required = false) final String description,
      @RequestParam(required = false) final Boolean unilateral,
      @RequestParam(required = false) final UUID loadTypeId,
      @RequestParam(required = false) final List<UUID> muscleSupGroupIds,
      @RequestParam(required = false) final UUID muscleGroupId,
      @RequestParam(required = false) final List<UUID> muscleSubGroupIds) {
    return ResponseEntity.ok(
        getExerciseControllerMapper.getResponse(
            getExerciseService.getAllExercises(
                getExerciseControllerMapper.getRequest(
                    GetExerciseRequest.builder()
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
