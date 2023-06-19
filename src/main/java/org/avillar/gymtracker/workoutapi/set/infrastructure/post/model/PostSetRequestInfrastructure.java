package org.avillar.gymtracker.workoutapi.set.infrastructure.post.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostSetRequestInfrastructure {

  @Size(max = 255, message = "The description must have a length between 0 and 255 characters")
  private String description;

  @NotNull
  @Size(max = 100, message = "The reps must be between 0 and 100")
  private Integer reps;

  @NotNull
  @Size(max = 10, message = "The rir must be between 0 and 10")
  private Double rir;

  @NotNull
  @Size(max = 1000, message = "The weight must be between 0 and 1000")
  private Double weight;
}
