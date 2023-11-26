package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record GetWorkoutSetGroupsResponse(
    UUID id, LocalDate date, String description, UUID userId, List<SetGroup> setGroups) {

  public record SetGroup(UUID id, Integer listOrder, String description, UUID exerciseId) {}
}
