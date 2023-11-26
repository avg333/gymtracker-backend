package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesupgroups.application;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSupGroup;

public interface GetMuscleSupGroupService {

  List<MuscleSupGroup> execute();
}
