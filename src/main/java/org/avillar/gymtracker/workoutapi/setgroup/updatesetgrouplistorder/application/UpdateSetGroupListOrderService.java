package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.ListOrderNotValidException;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;

public interface UpdateSetGroupListOrderService {

  List<SetGroup> execute(UUID setGroupId, int listOrder)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException, ListOrderNotValidException;
}
