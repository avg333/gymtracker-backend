package org.avillar.gymtracker.usersapi.modifysettings.infrastructure.model;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.avillar.gymtracker.usersapi.modifysettings.infrastructure.validator.SelectedPlatesValidator;

public record ModifySettingsRequestDto(
    @NotNull(message = "The internationalSystem field cannot be null") Boolean internationalSystem,
    @NotNull(message = "The selectedIncrement field cannot be null") Double selectedIncrement,
    @NotNull(message = "The selectedBar field cannot be null") Double selectedBar,
    @NotNull(message = "The selectedPlates field cannot be null") @SelectedPlatesValidator
        List<Double> selectedPlates) {}
