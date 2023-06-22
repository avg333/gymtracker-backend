package org.avillar.gymtracker.workoutapi.set.infrastructure.delete;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.set.application.delete.DeleteSetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class DeleteSetController {

  private final DeleteSetService deleteSetService;

  @DeleteMapping("sets/{setId}")
  public ResponseEntity<Void> delete(@PathVariable final UUID setId) {
    deleteSetService.execute(setId);
    return ResponseEntity.noContent().build();
  }
}
