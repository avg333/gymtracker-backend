package org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.model;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateSetGroupSetsRequest(@NotNull UUID setGroupId) {}
