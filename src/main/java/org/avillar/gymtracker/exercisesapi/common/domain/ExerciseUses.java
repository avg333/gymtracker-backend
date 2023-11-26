package org.avillar.gymtracker.exercisesapi.common.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExerciseUses {

  private UUID id;

  private Integer uses;

  private UUID userId;

  private Exercise exercise;
}
