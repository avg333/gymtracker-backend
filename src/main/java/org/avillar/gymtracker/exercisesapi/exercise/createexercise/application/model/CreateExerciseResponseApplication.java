package org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.model;

import java.util.UUID;
import lombok.Data;

@Data
public class CreateExerciseResponseApplication {

  private UUID id;
  private String name;
  private String description;
  private boolean unilateral;
}
