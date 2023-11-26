package org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.UpdateSetDataService;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.mapper.UpdateSetDataControllerMapper;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataRequestDto;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataResponseDto;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateSetDataControllerImpl implements UpdateSetDataController {

  private final UpdateSetDataService updateSetDataService;
  private final UpdateSetDataControllerMapper updateSetDataControllerMapper;

  @Override
  public UpdateSetDataResponseDto execute(
      final UUID setId, final UpdateSetDataRequestDto updateSetDataRequestDto)
      throws SetNotFoundException, WorkoutIllegalAccessException {
    return updateSetDataControllerMapper.map(
        updateSetDataService.execute(
            setId, updateSetDataControllerMapper.map(updateSetDataRequestDto)));
  }
}
