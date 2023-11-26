package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

public record GetAllLoadTypesResponse(
    @Schema(description = "LoadType id", example = "5819d9ab-5251-4916-9b5e-6cd36a01d560") UUID id,
    @Schema(description = "LoadType name", example = "bar") String name,
    @Schema(
            description = "LoadType description",
            example = "Weight guide machine used for weight training")
        String description) {}
