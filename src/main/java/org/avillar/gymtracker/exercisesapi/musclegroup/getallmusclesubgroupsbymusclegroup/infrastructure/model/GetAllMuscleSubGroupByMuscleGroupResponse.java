package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesubgroupsbymusclegroup.infrastructure.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

public record GetAllMuscleSubGroupByMuscleGroupResponse(
    @Schema(description = "MuscleSubGroup id", example = "5819d9ab-5251-4916-9b5e-6cd36a01d560")
        UUID id,
    @Schema(description = "MuscleSubGroup name", example = "Lower chest") String name,
    @Schema(description = "MuscleSubGroup name", example = "Lower part of the pectoral")
        String description) {}
