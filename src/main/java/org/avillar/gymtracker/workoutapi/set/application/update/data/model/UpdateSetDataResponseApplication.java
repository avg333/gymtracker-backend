package org.avillar.gymtracker.workoutapi.set.application.update.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSetDataResponseApplication {

  private String description;
  private Integer reps;
  private Double rir;
  private Double weight;
}
