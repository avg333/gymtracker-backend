package org.avillar.gymtracker.workoutapi.setgroup.application.update.listorder;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.listorder.model.UpdateSetGroupListOrderResponseApplication;

public interface UpdateSetGroupListOrderService {

  UpdateSetGroupListOrderResponseApplication execute(UUID setGroupId, int listOrder);
}
