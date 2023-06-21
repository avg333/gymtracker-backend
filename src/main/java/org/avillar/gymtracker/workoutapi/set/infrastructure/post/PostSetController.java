package org.avillar.gymtracker.workoutapi.set.infrastructure.post;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.set.application.post.PostSetService;
import org.avillar.gymtracker.workoutapi.set.infrastructure.post.mapper.PostSetControllerMapper;
import org.avillar.gymtracker.workoutapi.set.infrastructure.post.model.PostSetRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.set.infrastructure.post.model.PostSetResponseInfrastructure;
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
public class PostSetController {

  private final PostSetService postSetService;
  private final PostSetControllerMapper postSetControllerMapper;

  /**
   * Crea una nueva Set en el SetGroup especificado. Se usa en SetModal al guardar una Set nueva,
   * pero no usa el retorno.
   */
  @PostMapping("setGroups/{setGroupId}/sets")
  public ResponseEntity<PostSetResponseInfrastructure> postSet(
      @PathVariable final UUID setGroupId,
      @RequestBody final PostSetRequestInfrastructure postSetRequestInfrastructure) {
    return ResponseEntity.ok(
        postSetControllerMapper.postResponse(
            postSetService.post(
                setGroupId, postSetControllerMapper.postRequest(postSetRequestInfrastructure))));
  }
}
