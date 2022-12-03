package org.avillar.gymtracker.musclegroup.application;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleGroupDto;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleSubGroupDto;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleSupGroupDto;

import java.util.List;

public interface MuscleGroupService {

    List<MuscleSupGroupDto> getAllMuscleSupGroups();

    MuscleSupGroupDto getMuscleSupGroup(Long muscleSupGroupId) throws EntityNotFoundException;

    List<MuscleGroupDto> getAllMuscleSupGroupMuscleGroups(Long muscleSupGroupId) throws EntityNotFoundException;

    MuscleGroupDto getMuscleGroup(Long muscleGroupId) throws EntityNotFoundException;

    List<MuscleSubGroupDto> getAllMuscleGroupMuscleSubGroups(Long muscleGroupId) throws EntityNotFoundException;
}
