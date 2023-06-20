package org.avillar.gymtracker.workoutapi.set.infrastructure.update.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSetDataResponseInfrastructure {

  private String description;
  private Integer reps;
  private Double rir;
  private Double weight;
}
