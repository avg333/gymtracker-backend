package org.avillar.gymtracker.exercisesapi.loadtype.application.get;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.loadtype.application.get.model.GetLoadTypesApplicationResponse;

public interface GetLoadTypeService {

  List<GetLoadTypesApplicationResponse> execute();
}
