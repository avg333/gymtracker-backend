package org.avillar.gymtracker.workoutapi.set.getnewsetdata.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.application.model.GetNewSetDataResponseApplication;

public interface GetNewSetDataService {

  GetNewSetDataResponseApplication execute(UUID setGroupId);
}
