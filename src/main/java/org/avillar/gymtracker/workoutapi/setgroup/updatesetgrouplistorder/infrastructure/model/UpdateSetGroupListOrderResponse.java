package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model;

import java.util.UUID;

public record UpdateSetGroupListOrderResponse(
    UUID id, Integer listOrder, String description, UUID exerciseId, Workout workout) {

  public record Workout(UUID id) {}
}
