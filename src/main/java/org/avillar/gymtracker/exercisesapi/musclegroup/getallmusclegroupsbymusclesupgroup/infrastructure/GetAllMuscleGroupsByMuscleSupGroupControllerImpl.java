package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application.GetAllMuscleGroupsByMuscleSupGroupService;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure.mapper.GetAllMuscleGroupsByMuscleSupGroupControllerMapper;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure.model.GetAllMuscleGroupsByMuscleSupGroupResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetAllMuscleGroupsByMuscleSupGroupControllerImpl
    implements GetAllMuscleGroupsByMuscleSupGroupController {

  private final GetAllMuscleGroupsByMuscleSupGroupService getAllMuscleGroupsByMuscleSupGroupService;
  private final GetAllMuscleGroupsByMuscleSupGroupControllerMapper
      getAllMuscleGroupsByMuscleSupGroupControllerMapper;

  @Override
  public ResponseEntity<List<GetAllMuscleGroupsByMuscleSupGroupResponseInfrastructure>> execute(
      final UUID muscleSupGroupId) {
    return ResponseEntity.ok(
        getAllMuscleGroupsByMuscleSupGroupControllerMapper.map(
            getAllMuscleGroupsByMuscleSupGroupService.execute(muscleSupGroupId)));
  }
}
