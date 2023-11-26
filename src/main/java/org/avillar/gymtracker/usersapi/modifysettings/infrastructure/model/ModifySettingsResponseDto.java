package org.avillar.gymtracker.usersapi.modifysettings.infrastructure.model;

import java.util.List;

public record ModifySettingsResponseDto(
    Boolean internationalSystem,
    Double selectedIncrement,
    Double selectedBar,
    List<Double> selectedPlates) {}
