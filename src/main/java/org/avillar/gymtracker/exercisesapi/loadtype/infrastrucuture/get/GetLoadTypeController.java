package org.avillar.gymtracker.exercisesapi.loadtype.infrastrucuture.get;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.loadtype.application.get.GetLoadTypeService;
import org.avillar.gymtracker.exercisesapi.loadtype.infrastrucuture.get.mapper.GetLoadTypeControllerMapper;
import org.avillar.gymtracker.exercisesapi.loadtype.infrastrucuture.get.model.GetLoadTypeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${exercisesApiPrefix}")
@RequiredArgsConstructor
public class GetLoadTypeController {

  private final GetLoadTypeService getLoadTypeService;
  private final GetLoadTypeControllerMapper getLoadTypeControllerMapper;

  @GetMapping("/loadTypes/{loadTypeId}")
  public ResponseEntity<GetLoadTypeResponse> getLoadTypeById(@PathVariable final UUID loadTypeId) {
    return ResponseEntity.ok(
        getLoadTypeControllerMapper.getResponse(getLoadTypeService.getLoadType(loadTypeId)));
  }

  @GetMapping("/loadTypes")
  public ResponseEntity<List<GetLoadTypeResponse>> getAllLoadTypes() {
    return ResponseEntity.ok(
        getLoadTypeControllerMapper.getResponse(getLoadTypeService.getAllLoadTypes()));
  }
}
