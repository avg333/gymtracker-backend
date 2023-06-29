package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.model.UpdateSetListOrderResponseApplication;

public interface UpdateSetListOrderService {

  UpdateSetListOrderResponseApplication execute(UUID setId, int listOrder);
}
