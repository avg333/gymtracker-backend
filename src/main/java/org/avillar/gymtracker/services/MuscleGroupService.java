package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.MuscleGroup;
import org.avillar.gymtracker.model.MuscleSubGroup;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MuscleGroupService {

    List<MuscleGroup> getAllMuscleGroups();

    List<MuscleSubGroup> getMuscleSubGroups(Long muscleGroupId);
}
