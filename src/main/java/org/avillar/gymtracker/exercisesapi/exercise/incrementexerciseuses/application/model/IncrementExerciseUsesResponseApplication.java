package org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.application.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IncrementExerciseUsesResponseApplication {

  private int uses;
}
