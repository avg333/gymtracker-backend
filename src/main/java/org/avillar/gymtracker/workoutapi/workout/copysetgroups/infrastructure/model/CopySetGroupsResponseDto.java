package org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record CopySetGroupsResponseDto(
    UUID id,
    Integer listOrder,
    String description,
    UUID exerciseId,
    List<CopySetGroupsResponseDto.Set> sets) {
  public record Set(
      UUID id,
      Integer listOrder,
      String description,
      Integer reps,
      Double rir,
      Double weight,
      Date completedAt) {}
}
