package org.avillar.gymtracker.workoutapi.set.infrastructure.update.data;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.set.application.update.data.UpdateSetDataService;
import org.avillar.gymtracker.workoutapi.set.infrastructure.update.data.mapper.UpdateSetDataControllerMapper;
import org.avillar.gymtracker.workoutapi.set.infrastructure.update.data.model.UpdateSetDataRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.set.infrastructure.update.data.model.UpdateSetDataResponseInfrastructure;
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

  @PatchMapping("sets/{setId}")
  public ResponseEntity<UpdateSetDataResponseInfrastructure> patch(
      @PathVariable final UUID setId,
      @RequestBody final UpdateSetDataRequestInfrastructure updateSetDataRequestInfrastructure) {
    return ResponseEntity.ok(
        updateSetDataControllerMapper.map(
            updateSetDataService.execute(
                setId, updateSetDataControllerMapper.map(updateSetDataRequestInfrastructure))));
  }
}
