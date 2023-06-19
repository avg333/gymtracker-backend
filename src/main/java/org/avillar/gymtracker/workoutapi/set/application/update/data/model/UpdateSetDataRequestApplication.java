package org.avillar.gymtracker.workoutapi.set.application.update.data.model;

import lombok.Data;

@Data
public class UpdateSetDataRequestApplication {

  private String description;

  private Integer reps;

  private Double rir;

  private Double weight;
}
