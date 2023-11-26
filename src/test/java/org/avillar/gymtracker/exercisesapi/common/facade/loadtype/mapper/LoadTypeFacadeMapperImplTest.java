package org.avillar.gymtracker.exercisesapi.common.facade.loadtype.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.LoadTypeEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.LoadType;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT)
class LoadTypeFacadeMapperImplTest {

  private final LoadTypeFacadeMapperImpl mapper = new LoadTypeFacadeMapperImpl();

  @Test
  void shouldMapLoadTypeEntitiesToGetAllLoadTypes() {
    final List<LoadTypeEntity> source = Instancio.createList(LoadTypeEntity.class);

    final List<LoadType> target = mapper.map(source);
    assertThat(target).isNotNull().hasSize(source.size());
    for (int i = 0; i < source.size(); i++) {
      if (source.get(i) == null) {
        assertThat(target.get(i)).isNull();
        continue;
      }

      assertThat(target.get(i).getId()).isEqualTo(source.get(i).getId());
      assertThat(target.get(i).getName()).isEqualTo(source.get(i).getName());
      assertThat(target.get(i).getDescription()).isEqualTo(source.get(i).getDescription());
    }
  }

  @Test
  void shouldReturnNullWhenLoadTypeEntitiesIsNull() {
    assertThat(mapper.map(null)).isNotNull().isEmpty();
  }
}
