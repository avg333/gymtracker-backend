package org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model;

import java.util.UUID;
import lombok.Data;

@Data
public class CreateExerciseResponse {

  private UUID id;
  private String name;
  private String description;
  private boolean unilateral;
}
