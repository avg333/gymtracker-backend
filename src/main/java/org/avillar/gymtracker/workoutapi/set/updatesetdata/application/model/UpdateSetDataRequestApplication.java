package org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model;

import lombok.Data;

@Data
public class UpdateSetDataRequestApplication {

  private String description;
  private Integer reps;
  private Double rir;
  private Double weight;
  private Boolean completed;
}
