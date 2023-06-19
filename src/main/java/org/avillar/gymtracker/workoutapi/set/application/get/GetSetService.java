package org.avillar.gymtracker.workoutapi.set.application.get;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.application.get.model.GetSetResponseApplication;

public interface GetSetService {

  GetSetResponseApplication getSet(UUID setId);

  GetSetResponseApplication getSetDefaultDataForNewSet(UUID setGroupId);
}
