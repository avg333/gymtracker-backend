package org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.workout.WorkoutControllerDocumentation.WorkoutControllerTag;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.CopySetGroupsControllerDocumentation.Methods.CopySetGroupsDocumentation;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsRequestDto;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@WorkoutControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/")
public interface CopySetGroupsController {

  @CopySetGroupsDocumentation
  @PatchMapping("/workouts/{workoutId}/copySetGroups")
  @ResponseStatus(HttpStatus.OK)
  List<CopySetGroupsResponseDto> execute(
      @PathVariable UUID workoutId,
      @Valid @RequestBody CopySetGroupsRequestDto copySetGroupsRequestDto)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException;
}
