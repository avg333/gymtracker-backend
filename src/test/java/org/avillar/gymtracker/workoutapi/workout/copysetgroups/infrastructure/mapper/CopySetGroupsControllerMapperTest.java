package org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.CopySetGroupsServiceImpl.CopySetGroupsRequest;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsRequestDto;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsResponseDto;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

@Execution(ExecutionMode.CONCURRENT)
class CopySetGroupsControllerMapperTest {

  private final CopySetGroupsControllerMapper mapper =
      Mappers.getMapper(CopySetGroupsControllerMapper.class);

  private static void validateSetGroup(
      SetGroup setGroup, CopySetGroupsResponseDto copySetGroupsResponseDto) {
    if (setGroup == null) {
      assertThat(copySetGroupsResponseDto).isNull();
      return;
    }

    assertThat(copySetGroupsResponseDto.id()).isEqualTo(setGroup.getId());
    assertThat(copySetGroupsResponseDto.listOrder()).isEqualTo(setGroup.getListOrder());
    assertThat(copySetGroupsResponseDto.description()).isEqualTo(setGroup.getDescription());
    assertThat(copySetGroupsResponseDto.exerciseId()).isEqualTo(setGroup.getExerciseId());

    if (setGroup.getSets() == null) {
      assertThat(copySetGroupsResponseDto.sets()).isNull();
      return;
    }

    assertThat(copySetGroupsResponseDto.sets()).isNotNull().hasSameSizeAs(setGroup.getSets());
    for (int i = 0; i < setGroup.getSets().size(); i++) {
      validateSet(setGroup.getSets().get(i), copySetGroupsResponseDto.sets().get(i));
    }
  }

  private static void validateSet(Set set, CopySetGroupsResponseDto.Set setDto) {
    if (set == null) {
      assertThat(setDto).isNull();
      return;
    }

    assertThat(setDto.id()).isEqualTo(set.getId());
    assertThat(setDto.listOrder()).isEqualTo(set.getListOrder());
    assertThat(setDto.description()).isEqualTo(set.getDescription());
    assertThat(setDto.reps()).isEqualTo(set.getReps());
    assertThat(setDto.rir()).isEqualTo(set.getRir());
    assertThat(setDto.weight()).isEqualTo(set.getWeight());
    assertThat(setDto.completedAt()).isEqualTo(set.getCompletedAt());
  }

  @Test
  void shouldMapCopySetGroupsRequestDtoToCopySetGroupsRequest() {
    final CopySetGroupsRequestDto source = Instancio.create(CopySetGroupsRequestDto.class);

    final CopySetGroupsRequest map = mapper.map(source);
    assertThat(map).isNotNull();
    assertThat(map.getId()).isEqualTo(source.id());
    assertThat(map.getSource().name()).isEqualTo(source.source().name());
  }

  @Test
  void shouldReturnNullWhenCopySetGroupsRequestDtoIsNull() {
    final CopySetGroupsRequestDto source = null;
    assertThat(mapper.map(source)).isNull();
  }

  @Test
  void shouldMapSetGroupListToCopySetGroupsResponseDtoList() {
    final List<SetGroup> source = Instancio.createList(SetGroup.class);
    source.add(null);
    source.get(0).getSets().add(null);
    source.get(1).setSets(null);

    final List<CopySetGroupsResponseDto> result = mapper.map(source);
    assertThat(result).isNotNull().hasSameSizeAs(result);

    for (int i = 0; i < source.size(); i++) {
      final SetGroup setGroup = source.get(i);
      final CopySetGroupsResponseDto copySetGroupsResponseDto = result.get(i);
      validateSetGroup(setGroup, copySetGroupsResponseDto);
    }
  }

  @Test
  void shouldReturnNullWhenSetGroupListIsNull() {
    final List<SetGroup> source = null;
    assertThat(mapper.map(source)).isNull();
  }
}
