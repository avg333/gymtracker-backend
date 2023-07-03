package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application.model.UpdateSetGroupListOrderResponseApplication;

public interface UpdateSetGroupListOrderService {

  List<UpdateSetGroupListOrderResponseApplication> execute(UUID setGroupId, int listOrder)
      throws EntityNotFoundException, IllegalAccessException;
}
