package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure.model;

import jakarta.validation.constraints.Size;

public record UpdateSetGroupDescriptionRequest(
    @Size(max = 255, message = "Description should be between 0 and 255 characters")
        String description) {}
