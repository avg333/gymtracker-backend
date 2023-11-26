package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;

public interface GetSetGroupService {

  SetGroup execute(UUID setGroupId) throws SetGroupNotFoundException, WorkoutIllegalAccessException;
}
