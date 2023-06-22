package org.avillar.gymtracker.workoutapi.set.application.update.data;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.application.update.data.model.UpdateSetDataRequestApplication;
import org.avillar.gymtracker.workoutapi.set.application.update.data.model.UpdateSetDataResponseApplication;

public interface UpdateSetDataService {

  UpdateSetDataResponseApplication execute(
      UUID setId, UpdateSetDataRequestApplication updateSetDataRequestApplication);
}
