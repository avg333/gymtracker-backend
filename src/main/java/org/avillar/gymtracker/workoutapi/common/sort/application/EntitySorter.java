package org.avillar.gymtracker.workoutapi.common.sort.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.ListOrderNotValidException;
import org.avillar.gymtracker.workoutapi.common.sort.domain.SortableEntity;
import org.springframework.stereotype.Component;

@Component
public class EntitySorter {

  public static int getValidListOrder(final Integer listOrder, final int setsSize)
      throws ListOrderNotValidException {
    if (listOrder < 0 || listOrder > setsSize) {
      throw new ListOrderNotValidException(listOrder, 0, setsSize);
    }
    return listOrder;
  }

  /**
   * Return from a list of entities the entities whose list order needs to be updated when one
   * element from the list is deleted
   *
   * @param entities List of entities
   * @param entity Entity to be deleted
   * @param <T> Entity type
   */
  public <T extends SortableEntity> void sortDelete(
      final Collection<? extends SortableEntity> entities, final T entity) {
    entities.removeIf(e -> e.getId().equals(entity.getId()));

    final Iterator<? extends SortableEntity> iterator = entities.iterator();
    while (iterator.hasNext()) {
      final SortableEntity sortableEntity = iterator.next();
      // Decrement list order if it's greater than the deleted entity's list order
      if (sortableEntity.getListOrder() > entity.getListOrder()) {
        sortableEntity.setListOrder(sortableEntity.getListOrder() - 1);
      } else { // Remove if the element is in a position before the deleted entity's list order
        iterator.remove();
      }
    }
  }

  /**
   * Return from a list of entities the entities whose list order needs to be updated when one of
   * the entities updates its list order
   *
   * @param entities List of entities
   * @param entity Entity whose list order is updated
   * @param <T> Entity type
   */
  public <T extends SortableEntity> void sortUpdate(final Collection<T> entities, final T entity) {
    final int oldPosition =
        entities.stream()
            .filter(sg -> sg.getId().equals(entity.getId()))
            .findAny()
            .orElseThrow(InputMismatchException::new)
            .getListOrder();
    final int newPosition = entity.getListOrder();
    if (newPosition == oldPosition) {
      entities.clear();
      return;
    }

    // Update the list order of the entity that was updated
    entities.stream()
        .filter(sg -> sg.getId().equals(entity.getId()))
        .findAny()
        .ifPresent(sg -> sg.setListOrder(newPosition));

    // Sort the list of entities by list order
    ((List<T>) entities).sort(Comparator.comparing(SortableEntity::getListOrder));

    // Update the list order of the entities that were affected by the update and get their ids
    final List<UUID> updatedIds =
        newPosition > oldPosition
            ? sortWhenIncrementing(new ArrayList<>(entities), entity.getId(), newPosition)
            : sortWhenDecrementing(new ArrayList<>(entities), entity.getId(), newPosition);

    // Remove the entities that were not affected by the update
    entities.removeIf(e -> !updatedIds.contains(e.getId()));
  }

  // Go through the list of entities (sorted by listOrder) from the beginning to the end. When an
  // entity with the same listOrder as the updated entity is found (but not the updated entity)
  // increment its listOrder and add its id to the list of updated ids
  private List<UUID> sortWhenDecrementing(
      final List<SortableEntity> entities, final UUID updatedEntityId, final int newPosition) {
    final List<UUID> updatedIds = new ArrayList<>();
    int position = newPosition;
    for (final SortableEntity sortableEntity : entities) {
      if (sortableEntity.getId().equals(updatedEntityId)) {
        updatedIds.add(sortableEntity.getId());
        continue;
      }
      if (sortableEntity.getListOrder().equals(position)) {
        sortableEntity.setListOrder(sortableEntity.getListOrder() + 1);
        position++;
        updatedIds.add(sortableEntity.getId());
      }
    }
    return updatedIds;
  }

  // Go through the list of entities (sorted by listOrder) from the end to the beginning. When an
  // entity with the same listOrder as the updated entity is found (but not the updated entity)
  // decrement its listOrder and add its id to the list of updated ids
  private List<UUID> sortWhenIncrementing(
      final List<SortableEntity> entities, final UUID updatedEntityId, final int newPosition) {
    Collections.reverse(entities);
    final List<UUID> updatedIds = new ArrayList<>();
    int position = newPosition;
    for (final SortableEntity sortableEntity : entities) {
      if (sortableEntity.getId().equals(updatedEntityId)) {
        updatedIds.add(sortableEntity.getId());
        continue;
      }
      if (sortableEntity.getListOrder().equals(position)) {
        sortableEntity.setListOrder(sortableEntity.getListOrder() - 1);
        position--;
        updatedIds.add(sortableEntity.getId());
      }
    }
    return updatedIds;
  }
}
