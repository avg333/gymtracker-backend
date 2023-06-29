package org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetNewSetDataResponseInfrastructure {

  private String description;
  private Integer reps;
  private Double rir;
  private Double weight;
}
