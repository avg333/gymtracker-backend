package org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application.model.GetAllMuscleSupGroupsResponseApplication;

public interface GetMuscleSupGroupService {

  List<GetAllMuscleSupGroupsResponseApplication> execute();
}
