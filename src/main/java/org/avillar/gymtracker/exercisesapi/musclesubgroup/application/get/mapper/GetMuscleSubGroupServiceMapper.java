package org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get.model.GetMuscleSubGroupResponse;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.domain.MuscleSubGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetMuscleSubGroupServiceMapper {

  GetMuscleSubGroupResponse getResponse(MuscleSubGroup muscleSubGroup);

  List<GetMuscleSubGroupResponse> getResponse(List<MuscleSubGroup> muscleSubGroups);
}
