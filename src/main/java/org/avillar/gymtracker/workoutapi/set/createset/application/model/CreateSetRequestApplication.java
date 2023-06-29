package org.avillar.gymtracker.workoutapi.set.createset.application.model;

import lombok.Data;

@Data
public class CreateSetRequestApplication {

  private String description;
  private Integer reps;
  private Double rir;
  private Double weight;
}
