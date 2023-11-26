package org.avillar.gymtracker.workoutapi.common.sort.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import org.avillar.gymtracker.workoutapi.common.exception.application.ListOrderNotValidException;
import org.avillar.gymtracker.workoutapi.common.sort.domain.SortableEntity;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class EntitySorterTest {

  @InjectMocks private EntitySorter entitySorter;

  public static List<SortableEntity> generateNSortableEntityDummy(int n) {
    final List<SortableEntity> sortableEntities = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      sortableEntities.add(
          SortableEntityDummy.builder().id(UUID.randomUUID()).listOrder(i).build());
    }
    return sortableEntities;
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 1, 2, 3, 4})
  void shouldReturnValidListOrderForList(final Integer newListOrder)
      throws ListOrderNotValidException {
    final int totalElements = 5;
    final int newListOrderResult = EntitySorter.getValidListOrder(newListOrder, totalElements);

    if (newListOrder < 0 || newListOrder >= totalElements) {
      assertThat(newListOrderResult).isEqualTo(totalElements);
    } else {
      assertThat(newListOrderResult).isEqualTo(newListOrder);
    }
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, 6})
  void shouldThrowListOrderNotValidException(final Integer newListOrder) {
    final int totalElements = 5;
    final ListOrderNotValidException exception =
        new ListOrderNotValidException(newListOrder, 0, totalElements);

    assertThatThrownBy(() -> EntitySorter.getValidListOrder(newListOrder, totalElements))
        .isEqualTo(exception);
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 1, 2, 3, 4})
  void shouldReturnEntitiesWithTheirListOrderUpdatedWhenOneIsDeleted(int elementToDeleteIdx) {
    final int totalElements = 5;
    final List<SortableEntity> sortableEntities = generateNSortableEntityDummy(totalElements);
    final List<SortableEntity> sortableEntitiesCopy = new ArrayList<>(sortableEntities);
    final SortableEntity deletedSortableEntity = sortableEntities.get(elementToDeleteIdx);

    entitySorter.sortDelete(sortableEntities, deletedSortableEntity);
    assertThat(sortableEntities)
        .isNotNull()
        .hasSize(totalElements - elementToDeleteIdx - 1)
        .doesNotContain(deletedSortableEntity);

    for (int i = 0; i < sortableEntitiesCopy.size(); i++) {
      if (i <= elementToDeleteIdx) {
        assertThat(sortableEntities).doesNotContain(sortableEntitiesCopy.get(i));
      } else {
        final SortableEntity sortableEntityCopy = sortableEntitiesCopy.get(i);
        final SortableEntity sortableEntity = sortableEntities.get(i - elementToDeleteIdx - 1);
        assertThat(sortableEntity.getId()).isEqualTo(sortableEntityCopy.getId());
        assertThat(sortableEntity.getListOrder()).isEqualTo(i - 1);
      }
    }
  }

  @ParameterizedTest
  @CsvSource({
    "0, 0", "0, 1", "0, 2", "0, 3", "0, 4",
    "1, 0", "1, 1", "1, 2", "1, 3", "1, 4",
    "2, 0", "2, 1", "2, 2", "2, 3", "2, 4",
    "3, 0", "3, 1", "3, 2", "3, 3", "3, 4",
    "4, 0", "4, 1", "4, 2", "4, 3", "4, 4"
  })
  void shouldReturnEntitiesWithTheirListOrderUpdatedWhenOneIsUpdated(
      int elementToUpdateIdx, int newPosition) {
    final int totalElements = 5;
    final List<SortableEntity> sortableEntities = generateNSortableEntityDummy(totalElements);
    final List<SortableEntity> sortableEntitiesCopy = new ArrayList<>(sortableEntities);
    final SortableEntity updatedSortableEntity =
        SortableEntityDummy.builder()
            .id(sortableEntities.get(elementToUpdateIdx).getId())
            .listOrder(newPosition)
            .build();

    entitySorter.sortUpdate(sortableEntities, updatedSortableEntity);
    assertThat(sortableEntities).isNotNull();

    if (newPosition == elementToUpdateIdx) {
      assertThat(sortableEntities).isNotNull().isEmpty();
    }

    for (int i = 0; i < sortableEntitiesCopy.size(); i++) {
      final SortableEntity sortableEntityCopy = sortableEntitiesCopy.get(i);
      if (i < elementToUpdateIdx && i < newPosition) {
        assertThat(sortableEntities).doesNotContain(sortableEntityCopy);
      } else if (i > elementToUpdateIdx && i > newPosition) {
        assertThat(sortableEntities).doesNotContain(sortableEntityCopy);
      } else if (i == elementToUpdateIdx && !sortableEntities.isEmpty()) {
        assertThat(sortableEntityCopy.getListOrder()).isEqualTo(newPosition);
        assertThat(sortableEntities).contains(sortableEntityCopy);
      } else if (i < elementToUpdateIdx && i > newPosition) {
        assertThat(sortableEntityCopy.getListOrder()).isEqualTo(i + 1);
        assertThat(sortableEntities).contains(sortableEntityCopy);
      } else if (i > elementToUpdateIdx && i < newPosition) {
        assertThat(sortableEntityCopy.getListOrder()).isEqualTo(i - 1);
        assertThat(sortableEntities).contains(sortableEntityCopy);
      }
    }
  }

  @Data
  @Builder
  public static class SortableEntityDummy implements SortableEntity {
    private UUID id;
    private Integer listOrder;
  }
}
