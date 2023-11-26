package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesubgroupsbymusclegroup.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesubgroupsbymusclegroup.infrastructure.model.GetAllMuscleSubGroupByMuscleGroupResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class GetAllMuscleSubGroupByMuscleGroupControllerMapperTest {

  private final GetAllMuscleSubGroupByMuscleGroupControllerMapper mapper =
      Mappers.getMapper(GetAllMuscleSubGroupByMuscleGroupControllerMapper.class);

  @Test
  void shouldMapMuscleSubGroupToGetAllMuscleSubGroupByMuscleGroupResponseDtoSuccessfully() {
    final List<MuscleSubGroup> source = Instancio.createList(MuscleSubGroup.class);
    source.add(null);

    final List<GetAllMuscleSubGroupByMuscleGroupResponse> target = mapper.map(source);
    assertThat(target).isNotNull().hasSize(source.size());

    for (int i = 0; i < source.size(); i++) {
      if (source.get(i) == null) {
        assertThat(target.get(i)).isNull();
        continue;
      }

      assertThat(target.get(i)).isNotNull();
      assertThat(target.get(i).id()).isEqualTo(source.get(i).getId());
      assertThat(target.get(i).name()).isEqualTo(source.get(i).getName());
      assertThat(target.get(i).description()).isEqualTo(source.get(i).getDescription());
    }
  }

  @Test
  void shouldReturnNullWhenMuscleSubGroupIsNull() {
    assertThat(mapper.map(null)).isNull();
  }
}
