package org.avillar.gymtracker.workoutapi.set.infrastructure.update.data;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.set.application.update.data.UpdateSetDataService;
import org.avillar.gymtracker.workoutapi.set.infrastructure.update.data.mapper.UpdateSetDataControllerMapper;
import org.avillar.gymtracker.workoutapi.set.infrastructure.update.data.model.UpdateSetDataRequest;
import org.avillar.gymtracker.workoutapi.set.infrastructure.update.data.model.UpdateSetDataResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class UpdateSetDataController {

  private final UpdateSetDataService updateSetDataService;
  private final UpdateSetDataControllerMapper updateSetDataControllerMapper;

  /**
   * Sustituye la set con el ID especificado por otra con los datos del SetDto enviado. No se
   * sustituye el padre de la Set original ni el ID de la set. Devuelve el SetDto resultado de la
   * actualización. Se usa en SetModal cuando se modifica una Set existente, pero no se usa el
   * retorno.
   */
  @PatchMapping("sets/{setId}")
  public ResponseEntity<UpdateSetDataResponse> updateSetData(
      @PathVariable final UUID setId,
      @RequestBody final UpdateSetDataRequest updateSetDataRequest) {
    return ResponseEntity.ok(
        updateSetDataControllerMapper.updateResponse(
            updateSetDataService.update(
                setId, updateSetDataControllerMapper.updateRequest(updateSetDataRequest))));
  }
}
