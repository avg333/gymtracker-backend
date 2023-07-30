package org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.model.UpdateSetGroupSetsRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.model.UpdateSetGroupSetsResponseInfrastructure;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "SetGroups", description = "API to manage setGroups")
@RequestMapping(path = "${workoutsApiPrefix}/")
public interface UpdateSetGroupSetsController {

  @PatchMapping("/setGroups/{setGroupId}/sets")
  @ResponseStatus(HttpStatus.OK)
  UpdateSetGroupSetsResponseInfrastructure execute(
      @PathVariable UUID setGroupId,
      @Valid @RequestBody
          UpdateSetGroupSetsRequestInfrastructure updateSetGroupSetsRequestInfrastructure);
}
