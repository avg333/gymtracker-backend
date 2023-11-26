package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model;

import java.util.UUID;

public record CreateSetGroupResponse(
    UUID id, Integer listOrder, String description, UUID exerciseId, Workout workout) {

  public record Workout(UUID id) {}
}
