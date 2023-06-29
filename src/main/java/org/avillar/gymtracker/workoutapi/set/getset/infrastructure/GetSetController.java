package org.avillar.gymtracker.workoutapi.set.getset.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.set.getset.application.GetSetService;
import org.avillar.gymtracker.workoutapi.set.getset.infrastructure.mapper.GetSetControllerMapper;
import org.avillar.gymtracker.workoutapi.set.getset.infrastructure.model.GetSetResponseInfrastructure;
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
  public ResponseEntity<GetSetResponseInfrastructure> get(@PathVariable final UUID setId) {
    return ResponseEntity.ok(getSetControllerMapper.map(getSetService.execute(setId)));
  }
}
