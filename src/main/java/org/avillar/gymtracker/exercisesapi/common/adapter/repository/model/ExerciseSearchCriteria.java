package org.avillar.gymtracker.exercisesapi.common.adapter.repository.model;

import java.util.List;
import java.util.UUID;

public record ExerciseSearchCriteria(
    UUID userId,
    String name,
    String description,
    Boolean unilateral,
    List<UUID> loadTypes,
    List<UUID> muscleSupGroups,
    List<UUID> muscleGroups,
    List<UUID> muscleSubGroups) {}
