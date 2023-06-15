package org.avillar.gymtracker.workoutapi.set.application.update.data;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.application.update.data.model.UpdateSetDataRequest;
import org.avillar.gymtracker.workoutapi.set.application.update.data.model.UpdateSetDataResponse;

public interface UpdateSetDataService {

  UpdateSetDataResponse update(UUID setId, UpdateSetDataRequest updateSetDataRequest);
}
