package org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure.model;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetWorkoutsDateAndIdResponseInfrastructure {

  private Map<Date, UUID> workoutsDateAndId;
}
