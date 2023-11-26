package org.avillar.gymtracker.exercisesapi.common.facade.musclegroup.mapper;

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

@Execution(ExecutionMode.CONCURRENT)
class MuscleGroupFacadeMapperTest {

  private final MuscleGroupFacadeMapperImpl mapper = new MuscleGroupFacadeMapperImpl();

  @Test
  void shouldMapMuscleGroupEntityToMuscleGroup() {
    final List<MuscleGroupEntity> source = Instancio.createList(MuscleGroupEntity.class);

    final List<MuscleGroup> result = mapper.map(source);
    assertThat(result).isNotNull().hasSize(source.size());
    for (int i = 0; i < source.size(); i++) {
      if (source.get(i) == null) {
        assertThat(result.get(i)).isNull();
        continue;
      }

      assertThat(result.get(i).getId()).isEqualTo(source.get(i).getId());
      assertThat(result.get(i).getName()).isEqualTo(source.get(i).getName());
      assertThat(result.get(i).getDescription()).isEqualTo(source.get(i).getDescription());

      if (source.get(i).getMuscleSupGroups() == null) {
        assertThat(result.get(i).getMuscleSupGroups()).isNull();
      } else {
        assertThat(result.get(i).getMuscleSupGroups())
            .isNotNull()
            .hasSize(source.get(i).getMuscleSupGroups().size());
        List<MuscleSubGroupEntity> muscleSubGroupEntities =
            source.get(i).getMuscleSubGroups().stream().toList();
        List<MuscleSubGroup> muscleSubGroups = result.get(i).getMuscleSubGroups().stream().toList();
        for (int j = 0; j < muscleSubGroupEntities.size(); j++) {
          testMap(muscleSubGroupEntities.get(j), muscleSubGroups.get(j));
        }
      }

      if (source.get(i).getMuscleSupGroups() == null) {
        assertThat(result.get(i).getMuscleSupGroups()).isNull();
      } else {
        assertThat(result.get(i).getMuscleSupGroups())
            .isNotNull()
            .hasSize(source.get(i).getMuscleSupGroups().size());
        List<MuscleSupGroupEntity> muscleSupGroupEntities =
            source.get(i).getMuscleSupGroups().stream().toList();
        List<MuscleSupGroup> muscleSupGroups = result.get(i).getMuscleSupGroups().stream().toList();
        for (int j = 0; j < muscleSupGroupEntities.size(); j++) {
          testMap(muscleSupGroupEntities.get(j), muscleSupGroups.get(j));
        }
      }
    }
  }

  private void testMap(MuscleSupGroupEntity source, MuscleSupGroup target) {
    assertThat(target).isNotNull();
    assertThat(target.getId()).isEqualTo(source.getId());
    assertThat(target.getName()).isEqualTo(source.getName());
    assertThat(target.getDescription()).isEqualTo(source.getDescription());
    assertThat(target.getMuscleGroups()).isNotNull().isEmpty();
  }

  private void testMap(MuscleSubGroupEntity source, MuscleSubGroup target) {
    assertThat(target).isNotNull();
    assertThat(target.getId()).isEqualTo(source.getId());
    assertThat(target.getName()).isEqualTo(source.getName());
    assertThat(target.getDescription()).isEqualTo(source.getDescription());
    assertThat(target.getMuscleGroup()).isNull();
  }

  @Test
  void shouldReturnNullWhenMuscleGroupEntityIsNull() {
    assertThat(mapper.map(null)).isNotNull().isEmpty();
  }
}
