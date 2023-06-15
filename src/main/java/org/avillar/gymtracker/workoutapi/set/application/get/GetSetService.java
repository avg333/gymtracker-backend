package org.avillar.gymtracker.workoutapi.set.application.get;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.application.get.model.GetSetResponse;

public interface GetSetService {

  GetSetResponse getSet(UUID setId);

  GetSetResponse getSetDefaultDataForNewSet(UUID setGroupId);
}
