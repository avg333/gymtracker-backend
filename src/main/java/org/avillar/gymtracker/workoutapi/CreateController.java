package org.avillar.gymtracker.workoutapi;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class CreateController {

  private final WorkoutsDataLoader workoutsDataLoader;

  @PostMapping("/users/{userId}/create")
  public ResponseEntity<Void> postWorkout(@PathVariable final UUID userId) {
    workoutsDataLoader.createAll(userId);
    return ResponseEntity.noContent().build();
  }
}
