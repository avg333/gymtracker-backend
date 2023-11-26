package org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.model;

import java.sql.Date;
import java.util.UUID;

public interface WorkoutDateAndId {

  Date getDate();

  UUID getId();
}
