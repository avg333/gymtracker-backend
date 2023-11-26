package org.avillar.gymtracker.usersapi.getsettings.infrastructure.model;

import java.util.List;

public record GetSettingsResponse(
    Boolean internationalSystem,
    Double selectedIncrement,
    Double selectedBar,
    List<Double> selectedPlates) {}
