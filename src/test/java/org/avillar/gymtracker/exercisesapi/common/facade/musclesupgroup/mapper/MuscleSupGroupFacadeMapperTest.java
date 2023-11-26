package org.avillar.gymtracker.exercisesapi.common.facade.musclesupgroup.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleGroupEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleSubGroupEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleSupGroupEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSupGroup;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class MuscleSupGroupFacadeMapperTest {

  private final MuscleSupGroupFacadeMapper mapper =
      Mappers.getMapper(MuscleSupGroupFacadeMapper.class);

  @Test
  void shouldMapMuscleSupGroupToGetAllMuscleSupGroupsResponse() {
    final List<MuscleSupGroupEntity> source = Instancio.createList(MuscleSupGroupEntity.class);

    final List<MuscleSupGroup> target = mapper.map(source);
    assertThat(target).isNotNull().hasSize(source.size());
    for (int i = 0; i < source.size(); i++) {
      if (source.get(i) == null) {
        assertThat(target.get(i)).isNull();
        continue;
      }

      assertThat(target.get(i).getId()).isEqualTo(source.get(i).getId());
      assertThat(target.get(i).getName()).isEqualTo(source.get(i).getName());
      assertThat(target.get(i).getDescription()).isEqualTo(source.get(i).getDescription());

      if (source.get(i).getMuscleGroups() == null) {
        assertThat(target.get(i).getMuscleGroups()).isNull();
      } else {
        List<MuscleGroupEntity> muscleGroupEntities =
            source.get(i).getMuscleGroups().stream().toList();
        List<MuscleGroup> muscleGroups = target.get(i).getMuscleGroups().stream().toList();
        assertThat(muscleGroups).isNotNull().hasSize(muscleGroupEntities.size());
        for (int j = 0; j < muscleGroupEntities.size(); j++) {
          testMap(muscleGroupEntities.get(j), muscleGroups.get(j));
        }
      }
    }
  }

  private void testMap(MuscleGroupEntity source, MuscleGroup target) {
    assertThat(target).isNotNull();
    assertThat(target.getId()).isEqualTo(source.getId());
    assertThat(target.getName()).isEqualTo(source.getName());
    assertThat(target.getDescription()).isEqualTo(source.getDescription());
    assertThat(target.getMuscleSupGroups()).isNotNull().isEmpty();

    if (source.getMuscleSubGroups() == null) {
      assertThat(target.getMuscleSubGroups()).isNull();
    } else {
      List<MuscleSubGroupEntity> muscleSubGroupEntities =
          source.getMuscleSubGroups().stream().toList();
      List<MuscleSubGroup> muscleSubGroups = target.getMuscleSubGroups().stream().toList();
      assertThat(muscleSubGroups).isNotNull().hasSize(muscleSubGroupEntities.size());
      for (int j = 0; j < muscleSubGroupEntities.size(); j++) {
        testMap(muscleSubGroupEntities.get(j), muscleSubGroups.get(j));
      }
    }
  }

  private void testMap(MuscleSubGroupEntity source, MuscleSubGroup target) {
    assertThat(target).isNotNull();
    assertThat(target.getId()).isEqualTo(source.getId());
    assertThat(target.getName()).isEqualTo(source.getName());
    assertThat(target.getDescription()).isEqualTo(source.getDescription());
    assertThat(target.getMuscleGroup()).isNull();
  }

  @Test
  void shouldReturnNullWhenMuscleSupGroupEntityIsNull() {
    assertThat(mapper.map(null)).isNull();
  }
}
