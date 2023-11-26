package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure.model.GetAllMuscleGroupsByMuscleSupGroupResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class GetAllMuscleGroupsByMuscleSupGroupControllerMapperTest {

  private final GetAllMuscleGroupsByMuscleSupGroupControllerMapper mapper =
      Mappers.getMapper(GetAllMuscleGroupsByMuscleSupGroupControllerMapper.class);

  private static void validateMuscleGroup(
      GetAllMuscleGroupsByMuscleSupGroupResponse target, MuscleGroup source) {
    if (source == null) {
      assertThat(target).isNull();
      return;
    }

    assertThat(target.id()).isEqualTo(source.getId());
    assertThat(target.name()).isEqualTo(source.getName());
    assertThat(target.description()).isEqualTo(source.getDescription());

    if (source.getMuscleSubGroups() == null) {
      assertThat(target.muscleSubGroups()).isNull();
      return;
    }

    assertThat(target.muscleSubGroups()).isNotNull().hasSameSizeAs(source.getMuscleSubGroups());
    for (int i = 0; i < source.getMuscleSubGroups().size(); i++) {
      validateMuscleSubGroup(target.muscleSubGroups().get(i), source.getMuscleSubGroups().get(i));
    }
  }

  private static void validateMuscleSubGroup(
      GetAllMuscleGroupsByMuscleSupGroupResponse.MuscleSubGroup target, MuscleSubGroup source) {
    if (source == null) {
      assertThat(target).isNull();
      return;
    }

    assertThat(target).isNotNull();
    assertThat(target.id()).isEqualTo(source.getId());
    assertThat(target.name()).isEqualTo(source.getName());
    assertThat(target.description()).isEqualTo(source.getDescription());
  }

  @Test
  void shouldMapMuscleGroupToGetAllMuscleGroupsByMuscleSupGroupResponseDtoSuccessfully() {
    final List<MuscleGroup> source = Instancio.createList(MuscleGroup.class);
    source.get(0).getMuscleSubGroups().add(null);
    source.add(null);
    source.get(1).setMuscleSubGroups(null);

    final List<GetAllMuscleGroupsByMuscleSupGroupResponse> target = mapper.map(source);
    assertThat(target).isNotNull().hasSize(source.size());
    for (int i = 0; i < source.size(); i++) {
      validateMuscleGroup(target.get(i), source.get(i));
    }
  }

  @Test
  void shouldReturnNullWhenMuscleGroupIsNull() {
    assertThat(mapper.map(null)).isNull();
  }
}
