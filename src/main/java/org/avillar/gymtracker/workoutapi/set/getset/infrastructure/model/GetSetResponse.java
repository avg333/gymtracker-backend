package org.avillar.gymtracker.workoutapi.set.getset.infrastructure.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.utils.Constants;

public record GetSetResponse(
    UUID id,
    Integer listOrder,
    String description,
    Integer reps,
    Double rir,
    Double weight,
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = Constants.ISO_8601_DATE_FORMAT,
            timezone = Constants.UTC)
        Date completedAt,
    SetGroup setGroup) {
  public record SetGroup(UUID id) {}
}
