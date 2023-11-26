package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesupgroups.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSupGroup;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesupgroups.infrastructure.model.GetAllMuscleSupGroupsResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class GetAllMuscleSupGroupsControllerMapperTest {

  private final GetAllMuscleSupGroupsControllerMapper mapper =
      Mappers.getMapper(GetAllMuscleSupGroupsControllerMapper.class);

  private static void validateMuscleSupGroup(
      MuscleSupGroup source, GetAllMuscleSupGroupsResponse target) {
    if (source == null) {
      assertThat(target).isNull();
      return;
    }

    assertThat(target).isNotNull();
    assertThat(target.id()).isEqualTo(source.getId());
    assertThat(target.name()).isEqualTo(source.getName());
    assertThat(target.description()).isEqualTo(source.getDescription());

    if (source.getMuscleGroups() == null) {
      assertThat(target.muscleGroups()).isNull();
      return;
    }

    assertThat(target.muscleGroups()).isNotNull().hasSameSizeAs(source.getMuscleGroups());
    for (int j = 0; j < source.getMuscleGroups().size(); j++) {
      validateMuscleGroup(source.getMuscleGroups().get(j), target.muscleGroups().get(j));
    }
  }

  private static void validateMuscleGroup(
      MuscleGroup source, GetAllMuscleSupGroupsResponse.MuscleGroup target) {
    if (source == null) {
      assertThat(target).isNull();
      return;
    }

    assertThat(target).isNotNull();
    assertThat(target.id()).isEqualTo(source.getId());
    assertThat(target.name()).isEqualTo(source.getName());
    assertThat(target.description()).isEqualTo(source.getDescription());

    if (source.getMuscleSubGroups() == null) {
      assertThat(target.muscleSubGroups()).isNull();
      return;
    }

    assertThat(target.muscleSubGroups()).isNotNull().hasSameSizeAs(source.getMuscleSubGroups());
    for (int j = 0; j < source.getMuscleSubGroups().size(); j++) {
      validateMuscleSubGroup(source.getMuscleSubGroups().get(j), target.muscleSubGroups().get(j));
    }
  }

  private static void validateMuscleSubGroup(
      MuscleSubGroup source, GetAllMuscleSupGroupsResponse.MuscleGroup.MuscleSubGroup target) {
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
  void shouldMapMuscleSupGroupToGetAllMuscleSupGroupsResponseDto() {
    final List<MuscleSupGroup> source = Instancio.createList(MuscleSupGroup.class);
    source.add(null);
    source.get(0).getMuscleGroups().add(null);
    source.get(0).getMuscleGroups().get(0).getMuscleSubGroups().add(null);
    source.get(0).getMuscleGroups().get(1).setMuscleSubGroups(null);
    source.get(1).setMuscleGroups(null);

    final List<GetAllMuscleSupGroupsResponse> target = mapper.map(source);
    assertThat(target).isNotNull().hasSameSizeAs(source);
    for (int i = 0; i < source.size(); i++) {
      validateMuscleSupGroup(source.get(i), target.get(i));
    }
  }

  @Test
  void shouldReturnNullWhenMuscleSupGroupIsNull() {
    assertThat(mapper.map(null)).isNull();
  }
}
