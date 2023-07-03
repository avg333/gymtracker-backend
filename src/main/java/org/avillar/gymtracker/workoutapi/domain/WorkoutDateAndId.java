package org.avillar.gymtracker.workoutapi.domain;

import java.util.Date;
import java.util.UUID;

public interface WorkoutDateAndId {

  Date getDate();

  UUID getId();
}
