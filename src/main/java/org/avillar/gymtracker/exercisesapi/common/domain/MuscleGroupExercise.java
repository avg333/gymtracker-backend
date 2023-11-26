package org.avillar.gymtracker.exercisesapi.common.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MuscleGroupExercise {

  private UUID id;

  private Double weight;

  private MuscleGroup muscleGroup;

  private Exercise exercise;
}
