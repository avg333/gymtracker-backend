package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastrucuture.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.mapper.GetAllLoadTypesControllerMapper;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.model.GetAllLoadTypesResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class GetAllLoadTypesControllerMapperTest {

  private final GetAllLoadTypesControllerMapper mapper =
      Mappers.getMapper(GetAllLoadTypesControllerMapper.class);

  @Test
  void shouldMapLoadTypesToGetAllLoadTypesResponseDto() {
    final List<LoadType> source = Instancio.createList(LoadType.class);
    source.add(null);

    final List<GetAllLoadTypesResponse> target = mapper.map(source);
    assertThat(target).isNotNull().hasSize(source.size());
    for (int i = 0; i < source.size(); i++) {
      if (source.get(i) == null) {
        assertThat(target.get(i)).isNull();
        continue;
      }

      assertThat(target.get(i).id()).isEqualTo(source.get(i).getId());
      assertThat(target.get(i).name()).isEqualTo(source.get(i).getName());
      assertThat(target.get(i).description()).isEqualTo(source.get(i).getDescription());
    }
  }

  @Test
  void shouldReturnNullWhenLoadTypesIsNull() {
    assertThat(mapper.map(null)).isNull();
  }
}
