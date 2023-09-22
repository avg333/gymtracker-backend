package org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateSetRequest {

  @Size(max = 255, message = "The description must have a length between 0 and 255 characters")
  private String description;

  @NotNull
  @Min(value = 0, message = "The order must be between 0 and 100")
  @Max(value = 100, message = "The order must be between 0 and 100")
  private Integer reps;

  @NotNull
  @Min(value = 0, message = "The rir must be between 0 and 10")
  @Max(value = 10, message = "The rir must be between 0 and 10")
  private Double rir;

  @NotNull
  @Min(value = 0, message = "The weight must be between 0 and 1000")
  @Max(value = 1000, message = "The weight must be between 0 and 1000")
  private Double weight;

  @NotNull
  private Boolean completed;
}
