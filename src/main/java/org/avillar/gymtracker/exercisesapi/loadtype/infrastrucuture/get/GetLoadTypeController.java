package org.avillar.gymtracker.exercisesapi.loadtype.infrastrucuture.get;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.loadtype.application.get.GetLoadTypeService;
import org.avillar.gymtracker.exercisesapi.loadtype.infrastrucuture.get.mapper.GetLoadTypesControllerMapper;
import org.avillar.gymtracker.exercisesapi.loadtype.infrastrucuture.get.model.GetLoadTypesResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${exercisesApiPrefix}")
@RequiredArgsConstructor
public class GetLoadTypeController {

  private final GetLoadTypeService getLoadTypeService;
  private final GetLoadTypesControllerMapper getLoadTypesControllerMapper;

  @GetMapping("/loadTypes")
  public ResponseEntity<List<GetLoadTypesResponseInfrastructure>> get() {
    return ResponseEntity.ok(getLoadTypesControllerMapper.map(getLoadTypeService.execute()));
  }
}
