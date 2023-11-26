package org.avillar.gymtracker.exercisesapi.common.facade.musclesubgroup.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleSubGroupEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSubGroup;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT)
class MuscleSubGroupFacadeMapperTest {

  private final MuscleSubGroupFacadeMapperImpl mapper = new MuscleSubGroupFacadeMapperImpl();

  @Test
  void shouldMapMuscleSubGroupEntityToMuscleSubGroup() {
    final List<MuscleSubGroupEntity> source = Instancio.createList(MuscleSubGroupEntity.class);

    final List<MuscleSubGroup> result = mapper.map(source);
    assertThat(result).isNotNull().hasSize(source.size());
    for (int i = 0; i < source.size(); i++) {
      if (source.get(i) == null) {
        assertThat(result.get(i)).isNull();
        continue;
      }

      assertThat(result.get(i).getId()).isEqualTo(source.get(i).getId());
      assertThat(result.get(i).getName()).isEqualTo(source.get(i).getName());
      assertThat(result.get(i).getDescription()).isEqualTo(source.get(i).getDescription());

      final MuscleGroup muscleGroup = result.get(i).getMuscleGroup();
      if (source.get(i).getMuscleGroup() == null) {
        assertThat(muscleGroup).isNull();
        continue;
      }

      assertThat(muscleGroup).isNotNull();
      assertThat(muscleGroup.getId()).isEqualTo(source.get(i).getMuscleGroup().getId());
    }
  }

  @Test
  void shouldReturnNullWhenMuscleSubGroupEntityIsNull() {
    assertThat(mapper.map(null)).isNotNull().isEmpty();
  }
}
