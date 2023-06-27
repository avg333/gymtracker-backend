package org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure.model;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetWorkoutsDateAndIdResponseInfrastructure {

  private Map<Date, UUID> workoutsDateAndId;
}
