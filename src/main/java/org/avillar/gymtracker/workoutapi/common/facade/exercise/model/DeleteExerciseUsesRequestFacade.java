package org.avillar.gymtracker.workoutapi.common.facade.exercise.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;

@Getter
@Builder
public class DeleteExerciseUsesRequestFacade {

  @Default private List<ExerciseUses> exerciseUses = new ArrayList<>();

  @Getter
  @Builder
  public static class ExerciseUses {
    private UUID exerciseId;
    private int uses;
  }
}
