package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.model;

import java.util.UUID;

public record GetSetGroupResponse(
    UUID id, Integer listOrder, String description, UUID exerciseId, Workout workout) {

  public record Workout(UUID id) {}
}
