package org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.getexercisebyid;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisebyid.GetExerciseByIdService;
import org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.getexercisebyid.mapper.GetExerciseByIdControllerMapper;
import org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.getexercisebyid.model.GetExerciseByIdInfrastructureResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "${exercisesApiPrefix}")
@RequiredArgsConstructor
public class GetExerciseByIdController {

  private final GetExerciseByIdService getExerciseByIdService;
  private final GetExerciseByIdControllerMapper getExerciseByIdControllerMapper;

  @GetMapping("exercises/{exerciseId}")
  public ResponseEntity<GetExerciseByIdInfrastructureResponse> getExerciseById(
      @PathVariable final UUID exerciseId) {
    return ResponseEntity.ok(
        getExerciseByIdControllerMapper.map(getExerciseByIdService.execute(exerciseId)));
  }
}
