package org.avillar.gymtracker.workoutapi.set.application.get.set;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.application.get.set.model.GetSetResponseApplication;

public interface GetSetService {

  GetSetResponseApplication execute(UUID setId);
}
