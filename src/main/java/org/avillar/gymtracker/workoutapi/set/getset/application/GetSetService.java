package org.avillar.gymtracker.workoutapi.set.getset.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.getset.application.model.GetSetResponseApplication;

public interface GetSetService {

  GetSetResponseApplication execute(UUID setId);
}
