package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.dto.MuscleGroupDto;
import org.avillar.gymtracker.model.dto.MuscleSubGroupDto;
import org.avillar.gymtracker.model.dto.MuscleSupGroupDto;

import java.util.List;

public interface MuscleGroupService {

    List<MuscleSupGroupDto> getAllMuscleSupGroups();

    MuscleSupGroupDto getMuscleSupGroup(Long muscleSupGroupId);

    List<MuscleGroupDto> getAllMuscleGroups(Long muscleSupGroupId);

    MuscleGroupDto getMuscleGroup(Long muscleGroupId);

    List<MuscleSubGroupDto> getAllMuscleGroupMuscleSubGroups(Long muscleGroupId);
}
