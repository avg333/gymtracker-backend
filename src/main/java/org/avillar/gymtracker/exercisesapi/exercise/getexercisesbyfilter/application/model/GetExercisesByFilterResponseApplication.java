package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model;

import java.util.UUID;
import lombok.Data;

@Data
public class GetExercisesByFilterResponseApplication {

  private UUID id;
  private String name;
  private String description;
  private boolean unilateral;
}
