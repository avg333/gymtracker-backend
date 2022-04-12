package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.entities.MuscleGroup;
import org.avillar.gymtracker.model.entities.MuscleSubGroup;

import java.util.List;

public interface MuscleGroupService {

    List<MuscleGroup> getAllMuscleGroups();

    List<MuscleSubGroup> getMuscleSubGroups(Long muscleGroupId);
}
