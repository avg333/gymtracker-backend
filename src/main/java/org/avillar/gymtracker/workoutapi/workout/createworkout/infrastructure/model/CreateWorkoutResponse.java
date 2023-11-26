package org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model;

import java.time.LocalDate;
import java.util.UUID;

public record CreateWorkoutResponse(UUID id, LocalDate date, String description, UUID userId) {}
