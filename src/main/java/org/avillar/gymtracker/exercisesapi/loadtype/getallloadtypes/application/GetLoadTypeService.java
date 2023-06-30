package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.model.GetAllLoadTypesResponseApplication;

public interface GetLoadTypeService {

  List<GetAllLoadTypesResponseApplication> execute();
}
