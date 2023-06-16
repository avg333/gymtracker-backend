package org.avillar.gymtracker.exercisesapi.loadtype.application.get;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.loadtype.application.get.model.GetLoadTypeResponse;

public interface GetLoadTypeService {

  GetLoadTypeResponse getLoadType(UUID loadTypeId);

  List<GetLoadTypeResponse> getAllLoadTypes();
}
