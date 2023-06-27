package org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.workout.getworkout.application.GetWorkoutSimpleService;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.mapper.GetWorkoutSimpleControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.model.GetWorkoutSimpleResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class GetWorkoutSimpleController {

  private final GetWorkoutSimpleService getWorkoutSimpleServiceService;
  private final GetWorkoutSimpleControllerMapper getWorkoutSimpleControllerMapper;

  @GetMapping("/workouts/{workoutId}/simple")
  public ResponseEntity<GetWorkoutSimpleResponseInfrastructure> get(
      @PathVariable final UUID workoutId) {
    return ResponseEntity.ok(
        getWorkoutSimpleControllerMapper.map(getWorkoutSimpleServiceService.execute(workoutId)));
  }
}
