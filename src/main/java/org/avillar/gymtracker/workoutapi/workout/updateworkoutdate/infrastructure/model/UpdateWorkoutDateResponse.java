package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateWorkoutDateResponse {

  private Date date;
}
