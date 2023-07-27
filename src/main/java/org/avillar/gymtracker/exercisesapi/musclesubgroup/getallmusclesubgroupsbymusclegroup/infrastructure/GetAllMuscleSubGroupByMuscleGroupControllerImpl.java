package org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.infrastructure;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.application.GetAllMuscleSubGroupByMuscleGroupService;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.infrastructure.mapper.GetAllMuscleSubGroupByMuscleGroupControllerMapper;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.infrastructure.model.GetAllMuscleSubGroupByMuscleGroupResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GetAllMuscleSubGroupByMuscleGroupControllerImpl
    implements GetAllMuscleSubGroupByMuscleGroupController {

  private final GetAllMuscleSubGroupByMuscleGroupService getAllMuscleSubGroupByMuscleGroupService;
  private final GetAllMuscleSubGroupByMuscleGroupControllerMapper
      getAllMuscleSubGroupByMuscleGroupControllerMapper;

  @Override
  public List<GetAllMuscleSubGroupByMuscleGroupResponse> execute(final UUID muscleGroupId) {
    return getAllMuscleSubGroupByMuscleGroupControllerMapper.map(
        getAllMuscleSubGroupByMuscleGroupService.execute(muscleGroupId));
  }
}
