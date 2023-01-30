package org.avillar.gymtracker.musclegroup.application;

import org.avillar.gymtracker.errors.application.EntityNotFoundException;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleGroupDto;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleSubGroupDto;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleSupGroupDto;

import java.util.List;

public interface MuscleGroupService {

    List<MuscleSupGroupDto> getAllMuscleSupGroups();

    MuscleSupGroupDto getMuscleSupGroup(Long muscleSupGroupId) throws EntityNotFoundException;

    List<MuscleGroupDto> getAllMuscleSupGroupMuscleGroups(Long muscleSupGroupId) throws EntityNotFoundException;

    List<MuscleGroupDto> getAllMuscleGroups();

    MuscleGroupDto getMuscleGroup(Long muscleGroupId) throws EntityNotFoundException;

    List<MuscleSubGroupDto> getAllMuscleGroupMuscleSubGroups(Long muscleGroupId) throws EntityNotFoundException;
}
