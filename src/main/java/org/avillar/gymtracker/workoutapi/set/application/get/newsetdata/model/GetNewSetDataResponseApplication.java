package org.avillar.gymtracker.workoutapi.set.application.get.newsetdata.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetNewSetDataResponseApplication {

  private String description;
  private Integer reps;
  private Double rir;
  private Double weight;
}
