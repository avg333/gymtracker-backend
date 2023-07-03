package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.model.UpdateSetListOrderResponseApplication;

public interface UpdateSetListOrderService {

  List<UpdateSetListOrderResponseApplication> execute(UUID setId, int listOrder)
      throws EntityNotFoundException, IllegalAccessException;
}
