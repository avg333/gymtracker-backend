package org.avillar.gymtracker.musclegroup.application.dto;

import org.avillar.gymtracker.musclegroup.domain.MuscleSubGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MuscleSubGroupMapper {

    MuscleSubGroupDto toDto(MuscleSubGroup muscleSubGroup);

}
