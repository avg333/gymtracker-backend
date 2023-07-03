package org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model;

import lombok.Data;

@Data
public class UpdateSetDataResponseInfrastructure {

  private String description;
  private Integer reps;
  private Double rir;
  private Double weight;
}
