package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.post.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Data;

@Data
public class PostSetGroupRequestInfrastructure {

  @Size(max = 255, message = "The description must have a length between 0 and 255 characters")
  private String description;

  @NotNull(message = "The exerciseId is obligatory")
  private UUID exerciseId;
}
