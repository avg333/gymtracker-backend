package org.avillar.gymtracker.workoutapi.workout.application.get.summary.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetWorkoutSummaryResponseApplication {

  private int duration;
  private int exerciseNumber;
  private int setsNumber;
  private int weightVolume;
}
