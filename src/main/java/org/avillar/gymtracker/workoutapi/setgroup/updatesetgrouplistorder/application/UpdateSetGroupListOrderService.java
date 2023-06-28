package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application.model.UpdateSetGroupListOrderResponseApplication;

public interface UpdateSetGroupListOrderService {

  UpdateSetGroupListOrderResponseApplication execute(UUID setGroupId, int listOrder);
}
