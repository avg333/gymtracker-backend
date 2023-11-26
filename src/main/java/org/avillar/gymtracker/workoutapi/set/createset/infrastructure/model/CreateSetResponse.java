package org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model;

import static org.avillar.gymtracker.workoutapi.common.utils.Constants.ISO_8601_DATE_FORMAT;
import static org.avillar.gymtracker.workoutapi.common.utils.Constants.UTC;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.UUID;

public record CreateSetResponse(
    UUID id,
    Integer listOrder,
    String description,
    Integer reps,
    Double rir,
    Double weight,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ISO_8601_DATE_FORMAT, timezone = UTC)
        Date completedAt,
    SetGroup setGroup) {

  public record SetGroup(UUID id) {}
}
