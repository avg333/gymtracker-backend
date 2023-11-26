package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model;

import static org.avillar.gymtracker.workoutapi.common.utils.Constants.ISO_8601_DATE_FORMAT;
import static org.avillar.gymtracker.workoutapi.common.utils.Constants.UTC;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public record GetWorkoutDetailsResponseDto(
    UUID id, LocalDate date, String description, UUID userId, List<SetGroup> setGroups) {
  public record SetGroup(
      UUID id,
      Integer listOrder,
      String description,
      UUID exerciseId,
      List<Set> sets,
      Exercise exercise) {
    public record Set(
        UUID id,
        Integer listOrder,
        String description,
        Integer reps,
        Double rir,
        Double weight,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ISO_8601_DATE_FORMAT, timezone = UTC)
            Date completedAt) {}

    public record Exercise(
        UUID id,
        String name,
        String description,
        boolean unilateral,
        LoadType loadType,
        List<MuscleSubGroup> muscleSubGroups,
        List<MuscleGroup> muscleGroups) {

      public record LoadType(UUID id, String name, String description) {}

      public record MuscleSubGroup(UUID id, String name, String description) {}

      public record MuscleGroup(UUID id, String name, String description, Double weight) {}
    }
  }
}
