package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.sets.model;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class UpdateSetGroupSetsRequest {

  @NotNull private UUID setGroupId;
}
