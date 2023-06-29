package org.avillar.gymtracker.workoutapi.set.getnewsetdata.application.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetNewSetDataResponseApplication {

  private String description;
  private Integer reps;
  private Double rir;
  private Double weight;
}