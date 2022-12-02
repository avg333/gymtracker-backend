package org.avillar.gymtracker.musclegroup.application.dto;

import org.avillar.gymtracker.musclegroup.domain.MuscleGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MuscleGroupMapper {


    MuscleGroupDto toDto(MuscleGroup muscleGroup);

}
