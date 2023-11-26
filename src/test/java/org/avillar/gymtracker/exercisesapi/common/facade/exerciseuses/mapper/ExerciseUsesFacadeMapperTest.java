package org.avillar.gymtracker.exercisesapi.common.facade.exerciseuses.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseUsesEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.ExerciseUses;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT)
class ExerciseUsesFacadeMapperTest {

  private final ExerciseUsesFacadeMapper mapper = new ExerciseUsesFacadeMapperImpl();

  @Test
  void shouldMapExerciseUsesListToExerciseUsesEntityListSuccessfully() {
    final List<ExerciseUses> source = Instancio.createList(ExerciseUses.class);

    final List<ExerciseUsesEntity> result = mapper.mapExerciseUsesList(source);
    assertThat(result).isNotNull().hasSize(source.size());
    for (int i = 0; i < source.size(); i++) {
      if (source.get(i) == null) {
        assertThat(result.get(i)).isNull();
        continue;
      }

      assertThat(result.get(i).getId()).isEqualTo(source.get(i).getId());
      assertThat(result.get(i).getUses()).isEqualTo(source.get(i).getUses());
      assertThat(result.get(i).getUserId()).isEqualTo(source.get(i).getUserId());
      if (source.get(i).getExercise() == null) {
        assertThat(result.get(i).getExercise()).isNull();
      } else {
        assertThat(result.get(i).getExercise().getId())
            .isEqualTo(source.get(i).getExercise().getId());
      }
    }
  }

  @Test
  void shouldReturnNullWhenExerciseUsesListIsNull() {
    assertThat(mapper.mapExerciseUsesList(null)).isNull();
  }

  @Test
  void shouldMapExerciseUsesEntityListToExerciseUsesListSuccessfully() {
    final List<ExerciseUsesEntity> source = Instancio.createList(ExerciseUsesEntity.class);

    final List<ExerciseUses> result = mapper.mapExerciseUsesEntityList(source);
    assertThat(result).isNotNull().hasSize(source.size());
    for (int i = 0; i < source.size(); i++) {
      if (source.get(i) == null) {
        assertThat(result.get(i)).isNull();
        continue;
      }

      assertThat(result.get(i).getId()).isEqualTo(source.get(i).getId());
      assertThat(result.get(i).getUses()).isEqualTo(source.get(i).getUses());
      assertThat(result.get(i).getUserId()).isEqualTo(source.get(i).getUserId());
      if (source.get(i).getExercise() == null) {
        assertThat(result.get(i).getExercise()).isNull();
      } else {
        assertThat(result.get(i).getExercise().getId())
            .isEqualTo(source.get(i).getExercise().getId());
      }
    }
  }

  @Test
  void shouldReturnNullWhenExerciseUsesEntityListIsNull() {
    assertThat(mapper.mapExerciseUsesEntityList(null)).isNull();
  }
}
