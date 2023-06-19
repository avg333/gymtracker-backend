package org.avillar.gymtracker.workoutapi.workout.infrastructure.get.idanddate.model;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetWorkoutIdAndDateResponseInfrastructure {

  private Map<Date, UUID> workoutsIdAndDate;
}
