package org.avillar.gymtracker.workoutapi.set.infrastructure.get;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.set.application.get.GetSetService;
import org.avillar.gymtracker.workoutapi.set.infrastructure.get.mapper.GetSetControllerMapper;
import org.avillar.gymtracker.workoutapi.set.infrastructure.get.model.GetSetResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// FINALIZAR
@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class GetSetController {

  private final GetSetService getSetService;
  private final GetSetControllerMapper getSetControllerMapper;

  /**
   * Obtiene el setDTO del set con el ID especificado Se usa cuando en SetModal cuando se modifica
   * una set existente
   */
  @GetMapping("sets/{setId}")
  public ResponseEntity<GetSetResponseInfrastructure> getSet(@PathVariable final UUID setId) {
    return ResponseEntity.ok(getSetControllerMapper.getResponse(getSetService.getSet(setId)));
  }

  /** Obtiene una Set con los datos por defecto para una nueva set en ese SetGroup */
  @GetMapping("setGroups/{setGroupId}/sets/newSet")
  public ResponseEntity<GetSetResponseInfrastructure> getSetDefaultDataForNewSet(
      @PathVariable final UUID setGroupId) {
    return ResponseEntity.ok(
        getSetControllerMapper.getResponse(getSetService.getSetDefaultDataForNewSet(setGroupId)));
  }
}
