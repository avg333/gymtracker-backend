package org.avillar.gymtracker.musclegroup.application;

import org.avillar.gymtracker.musclegroup.application.dto.MuscleGroupDto;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleSubGroupDto;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleSupGroupDto;

import java.util.List;

public interface MuscleGroupService {

    List<MuscleSupGroupDto> getAllMuscleSupGroups();

    MuscleSupGroupDto getMuscleSupGroup(Long muscleSupGroupId);

    List<MuscleGroupDto> getAllMuscleSupGroupMuscleGroups(Long muscleSupGroupId);

    MuscleGroupDto getMuscleGroup(Long muscleGroupId);

    List<MuscleSubGroupDto> getAllMuscleGroupMuscleSubGroups(Long muscleGroupId);
}
