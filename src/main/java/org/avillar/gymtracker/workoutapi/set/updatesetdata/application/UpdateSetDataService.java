package org.avillar.gymtracker.workoutapi.set.updatesetdata.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataRequest;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataResponse;

public interface UpdateSetDataService {

  UpdateSetDataResponse execute(UUID setId, UpdateSetDataRequest updateSetDataRequest)
      throws SetNotFoundException, WorkoutIllegalAccessException;
}
