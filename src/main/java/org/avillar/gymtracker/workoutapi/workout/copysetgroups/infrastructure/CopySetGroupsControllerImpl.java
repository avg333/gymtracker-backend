package org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.CopySetGroupsService;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.mapper.CopySetGroupsControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsRequestDto;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsResponseDto;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CopySetGroupsControllerImpl implements CopySetGroupsController {

  private final CopySetGroupsService copySetGroupsService;
  private final CopySetGroupsControllerMapper copySetGroupsControllerMapper;

  @Override
  public List<CopySetGroupsResponseDto> execute(
      final UUID workoutId, final CopySetGroupsRequestDto copySetGroupsRequestDto)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {
    return copySetGroupsControllerMapper.map(
        copySetGroupsService.execute(
            workoutId, copySetGroupsControllerMapper.map(copySetGroupsRequestDto)));
  }
}
