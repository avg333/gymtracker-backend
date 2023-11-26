package org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.model;

import java.time.LocalDate;
import java.util.UUID;

public record GetWorkoutResponse(UUID id, LocalDate date, String description, UUID userId) {}
