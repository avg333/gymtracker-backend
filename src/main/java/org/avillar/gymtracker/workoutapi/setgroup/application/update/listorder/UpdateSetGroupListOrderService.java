package org.avillar.gymtracker.workoutapi.setgroup.application.update.listorder;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.listorder.model.UpdateSetGroupListOrderResponse;

public interface UpdateSetGroupListOrderService {

  UpdateSetGroupListOrderResponse update(UUID setGroupId, int listOrder);
}
