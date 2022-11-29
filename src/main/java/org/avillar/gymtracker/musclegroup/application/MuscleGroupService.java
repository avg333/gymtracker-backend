package org.avillar.gymtracker.musclegroup.application;

import java.util.List;

public interface MuscleGroupService {

    List<MuscleSupGroupDto> getAllMuscleSupGroups();

    MuscleSupGroupDto getMuscleSupGroup(Long muscleSupGroupId);

    List<MuscleGroupDto> getAllMuscleGroups(Long muscleSupGroupId);

    MuscleGroupDto getMuscleGroup(Long muscleGroupId);

    List<MuscleSubGroupDto> getAllMuscleGroupMuscleSubGroups(Long muscleGroupId);
}
