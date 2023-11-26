package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.exception.application.ListOrderNotValidException;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;

public interface UpdateSetListOrderService {

  List<Set> execute(UUID setId, int listOrder)
      throws SetNotFoundException, WorkoutIllegalAccessException, ListOrderNotValidException;
}
