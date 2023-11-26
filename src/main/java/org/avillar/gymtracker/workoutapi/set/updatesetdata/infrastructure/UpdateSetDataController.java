package org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure;

import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.SetControllerDocumentation.SetControllerTag;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.UpdateSetDataControllerDocumentation.Methods.UpdateSetDataDocumentation;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataRequestDto;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@SetControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1/")
public interface UpdateSetDataController {

  @UpdateSetDataDocumentation
  @PatchMapping("sets/{setId}")
  @ResponseStatus(HttpStatus.OK)
  UpdateSetDataResponseDto execute(
      @PathVariable UUID setId, @Valid @RequestBody UpdateSetDataRequestDto updateSetDataRequestDto)
      throws SetNotFoundException, WorkoutIllegalAccessException;
}
