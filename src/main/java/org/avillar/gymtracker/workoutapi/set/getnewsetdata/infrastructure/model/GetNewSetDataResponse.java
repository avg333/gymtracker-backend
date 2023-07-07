package org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure.model;

import lombok.Data;

@Data
public class GetNewSetDataResponse {

  private String description;
  private Integer reps;
  private Double rir;
  private Double weight;
}
