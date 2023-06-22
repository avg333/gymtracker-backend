package org.avillar.gymtracker.workoutapi.set.infrastructure.get.newsetdata.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetNewSetDataResponseInfrastructure {

  private String description;
  private Integer reps;
  private Double rir;
  private Double weight;
}
