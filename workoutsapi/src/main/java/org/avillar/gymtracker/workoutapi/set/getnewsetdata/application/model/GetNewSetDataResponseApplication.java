package org.avillar.gymtracker.workoutapi.set.getnewsetdata.application.model;

import lombok.Data;

@Data
public class GetNewSetDataResponseApplication {

  private String description;
  private Integer reps;
  private Double rir;
  private Double weight;
}
