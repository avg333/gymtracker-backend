package org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.application.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DecrementExerciseUsesResponseApplication {

  private int uses;
}
