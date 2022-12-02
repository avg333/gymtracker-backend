package org.avillar.gymtracker.musclegroup.application.dto;

import org.avillar.gymtracker.musclegroup.domain.MuscleSupGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MuscleSupGroupMapper {

    MuscleSupGroupDto toDto(MuscleSupGroup muscleGroup);

}
