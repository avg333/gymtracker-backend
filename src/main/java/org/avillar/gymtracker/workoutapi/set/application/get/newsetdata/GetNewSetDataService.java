package org.avillar.gymtracker.workoutapi.set.application.get.newsetdata;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.application.get.newsetdata.model.GetNewSetDataResponseApplication;

public interface GetNewSetDataService {

  GetNewSetDataResponseApplication getNewSetData(UUID setGroupId);
}
