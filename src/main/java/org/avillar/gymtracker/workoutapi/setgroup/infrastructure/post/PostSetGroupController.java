package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.post;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.setgroup.application.post.PostSetGroupService;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.post.mapper.PostSetGroupControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.post.model.PostSetGroupRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.post.model.PostSetGroupResponseInfrastructure;
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
public class PostSetGroupController {

  private final PostSetGroupService postSetGroupService;
  private final PostSetGroupControllerMapper postSetGroupControllerMapper;

  /** Se usa cuando se crea una nueva setGroup en el Workout. No usa el retorno */
  @PostMapping("/workouts/{workoutId}/setGroups")
  public ResponseEntity<PostSetGroupResponseInfrastructure> post(
      @PathVariable final UUID workoutId,
      @Valid @RequestBody
          final PostSetGroupRequestInfrastructure postSetGroupRequestInfrastructure) {
    return ResponseEntity.ok(
        postSetGroupControllerMapper.postResponse(
            postSetGroupService.post(
                workoutId,
                postSetGroupControllerMapper.postRequest(postSetGroupRequestInfrastructure))));
  }
}
