package org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.infrastrucure;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.infrastrucure.model.GetAllMuscleSupGroupsResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${exercisesApiPrefix}")
public interface GetAllMuscleSupGroupsController {

  @GetMapping("muscleSupGroups")
  ResponseEntity<List<GetAllMuscleSupGroupsResponseInfrastructure>> execute();
}
