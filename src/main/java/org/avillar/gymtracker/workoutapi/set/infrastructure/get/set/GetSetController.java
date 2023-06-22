package org.avillar.gymtracker.workoutapi.set.infrastructure.get.set;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.set.application.get.set.GetSetService;
import org.avillar.gymtracker.workoutapi.set.infrastructure.get.set.mapper.GetSetControllerMapper;
import org.avillar.gymtracker.workoutapi.set.infrastructure.get.set.model.GetSetResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class GetSetController {

  private final GetSetService getSetService;
  private final GetSetControllerMapper getSetControllerMapper;

  @GetMapping("sets/{setId}")
  public ResponseEntity<GetSetResponseInfrastructure> getSet(@PathVariable final UUID setId) {
    return ResponseEntity.ok(getSetControllerMapper.getResponse(getSetService.getSet(setId)));
  }
}
