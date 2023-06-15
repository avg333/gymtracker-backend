package org.avillar.gymtracker.workoutapi.set.application.update.listorder;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.application.update.listorder.model.UpdateSetListOrderResponse;

public interface UpdateSetListOrderService {

  UpdateSetListOrderResponse updateSetListOrder(UUID setId, int listOrder);
}
