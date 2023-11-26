package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.model;

import static org.avillar.gymtracker.workoutapi.common.utils.Constants.ISO_8601_DATE_FORMAT;
import static org.avillar.gymtracker.workoutapi.common.utils.Constants.UTC;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public record GetSetGroupsByExerciseResponse(
    UUID id,
    Integer listOrder,
    String description,
    UUID exerciseId,
    Workout workout,
    List<Set> sets) {

  public record Workout(UUID id) {}

  public record Set(
      UUID id,
      Integer listOrder,
      String description,
      Integer reps,
      Double rir,
      Double weight,
      @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ISO_8601_DATE_FORMAT, timezone = UTC)
          Date completedAt) {}
}
