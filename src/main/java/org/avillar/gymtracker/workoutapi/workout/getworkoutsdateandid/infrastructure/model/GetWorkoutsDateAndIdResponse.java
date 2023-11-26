package org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetWorkoutsDateAndIdResponse {

  private Map<LocalDate, UUID> workoutsDateAndId;
}
