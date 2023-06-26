package org.avillar.gymtracker.workoutapi.set.application.update.data.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateSetDataResponseApplication {

  private String description;
  private Integer reps;
  private Double rir;
  private Double weight;
}
