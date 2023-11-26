package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model;

import java.util.List;
import java.util.UUID;

public record GetExercisesByFilterRequest(
    // TODO Add schema
    String name,
    String description,
    Boolean unilateral,
    List<UUID> loadTypeIds,
    List<UUID> muscleSupGroupIds,
    List<UUID> muscleGroupIds,
    List<UUID> muscleSubGroupIds) {}
