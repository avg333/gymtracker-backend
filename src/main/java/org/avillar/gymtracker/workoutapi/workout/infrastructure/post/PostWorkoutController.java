package org.avillar.gymtracker.workoutapi.workout.infrastructure.post;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.workout.application.post.PostWorkoutService;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.post.mapper.PostWorkoutControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.post.model.PostWorkoutRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.post.model.PostWorkoutResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// RDY
@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class PostWorkoutController {

  private final PostWorkoutService postWorkoutService;
  private final PostWorkoutControllerMapper postWorkoutControllerMapper;

  @PostMapping("/users/{userId}/workouts")
  public ResponseEntity<PostWorkoutResponseInfrastructure> postWorkout(
      @PathVariable final UUID userId,
      @Valid @RequestBody final PostWorkoutRequestInfrastructure postWorkoutRequestInfrastructure) {
    return ResponseEntity.ok(
        postWorkoutControllerMapper.postResponse(
            postWorkoutService.post(
                userId,
                postWorkoutControllerMapper.postRequest(postWorkoutRequestInfrastructure))));
  }
}
