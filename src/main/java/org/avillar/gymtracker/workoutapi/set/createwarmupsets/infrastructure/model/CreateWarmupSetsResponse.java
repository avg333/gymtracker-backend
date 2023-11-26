package org.avillar.gymtracker.workoutapi.set.createwarmupsets.infrastructure.model;

import java.util.UUID;

public record CreateWarmupSetsResponse(
    UUID id, Integer listOrder, String description, Integer reps, Double rir, Double weight) {}
