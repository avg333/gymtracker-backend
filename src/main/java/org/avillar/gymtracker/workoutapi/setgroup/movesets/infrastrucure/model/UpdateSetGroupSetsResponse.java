package org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.model;

import java.util.UUID;

public record UpdateSetGroupSetsResponse(
    UUID id, Integer listOrder, String description, Integer reps, Double rir, Double weight) {}
