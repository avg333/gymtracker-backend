package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.domain.LoadType;

public interface GetLoadTypeService {

  List<LoadType> execute();
}
