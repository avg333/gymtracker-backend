package org.avillar.gymtracker.workoutapi.set.infrastructure.get.newsetdata;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.set.application.get.newsetdata.GetNewSetDataService;
import org.avillar.gymtracker.workoutapi.set.infrastructure.get.newsetdata.mapper.GetNewSetDataControllerMapper;
import org.avillar.gymtracker.workoutapi.set.infrastructure.get.newsetdata.model.GetNewSetDataResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class GetNewSetDataController {

  private final GetNewSetDataService getNewSetDataService;
  private final GetNewSetDataControllerMapper getNewSetDataControllerMapper;

  @GetMapping("setGroups/{setGroupId}/sets/newSet")
  public ResponseEntity<GetNewSetDataResponseInfrastructure> getSetDefaultDataForNewSet(
      @PathVariable final UUID setGroupId) {
    return ResponseEntity.ok(
        getNewSetDataControllerMapper.getResponse(getNewSetDataService.getNewSetData(setGroupId)));
  }
}
