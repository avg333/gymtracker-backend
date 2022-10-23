package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.dto.MuscleGroupDto;
import org.avillar.gymtracker.model.dto.MuscleSubGroupDto;

import java.util.List;

public interface MuscleGroupService {

    List<MuscleGroupDto> getAllMuscleGroups();

    List<MuscleSubGroupDto> getAllMuscleGroupMuscleSubGroups(Long muscleGroupId);
}
