package org.avillar.gymtracker.workoutapi.set.application.update.listorder;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.application.update.listorder.model.UpdateSetListOrderResponseApplication;

public interface UpdateSetListOrderService {

  UpdateSetListOrderResponseApplication execute(UUID setId, int listOrder);
}
