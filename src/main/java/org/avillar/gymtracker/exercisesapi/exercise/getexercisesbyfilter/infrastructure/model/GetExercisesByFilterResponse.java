package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model;

import java.util.UUID;
import lombok.Data;

@Data
public class GetExercisesByFilterResponse {

  private UUID id;
  private String name;
  private String description;
  private boolean unilateral;
}
