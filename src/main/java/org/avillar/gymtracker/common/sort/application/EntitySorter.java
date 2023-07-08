package org.avillar.gymtracker.common.sort.application;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import org.avillar.gymtracker.common.sort.domain.SortableEntity;
import org.springframework.stereotype.Component;

@Component
public class EntitySorter {

  public static int getValidListOrder(final Integer listOrder, final int setsSize) {
    return (null == listOrder || listOrder > setsSize || 0 > listOrder) ? setsSize : listOrder;
  }

  public <T extends SortableEntity> void sortDelete(
      final Collection<? extends SortableEntity> entities, final T entity) {
    entities.removeIf(e -> e.getId().equals(entity.getId()));

    final Iterator<? extends SortableEntity> iterator = entities.iterator();
    while (iterator.hasNext()) {
      final SortableEntity sortableEntity = iterator.next();
      if (sortableEntity.getListOrder() > entity.getListOrder()) {
        sortableEntity.setListOrder(sortableEntity.getListOrder() - 1);
      } else {
        iterator.remove();
      }
    }
  }

  public <T extends SortableEntity> void sortUpdate(
      final Collection<? extends SortableEntity> entities, final T entity, final int oldPosition) {
    final int newPosition = entity.getListOrder();
    if (newPosition == oldPosition) {
      entities.clear();
      return;
    }

    entities.removeIf(e -> e.getId().equals(entity.getId()));

    final Iterator<? extends SortableEntity> iterator = entities.iterator();
    while (iterator.hasNext()) {
      final SortableEntity sortableEntity = iterator.next();
      final boolean isSamePosition =
          Objects.equals(entity.getListOrder(), sortableEntity.getListOrder());
      final boolean lessThanMax =
          sortableEntity.getListOrder() < Math.max(oldPosition, newPosition);
      final boolean moreThanMin =
          sortableEntity.getListOrder() > Math.min(oldPosition, newPosition);
      if (isSamePosition || (lessThanMax && moreThanMin)) {
        sortableEntity.setListOrder(
            sortableEntity.getListOrder() + oldPosition > newPosition ? 1 : -1);
      } else {
        iterator.remove();
      }
    }
  }

  @Deprecated(forRemoval = true)
  public <T extends SortableEntity> void sortPost(
      final Collection<? extends SortableEntity> entities, final T entity) {
    entities.removeIf(e -> e.getId().equals(entity.getId()));

    final Iterator<? extends SortableEntity> iterator = entities.iterator();
    while (iterator.hasNext()) {
      final SortableEntity sortableEntity = iterator.next();
      if (entity.getListOrder() <= sortableEntity.getListOrder()) {
        sortableEntity.setListOrder(sortableEntity.getListOrder() + 1);
      } else {
        iterator.remove();
      }
    }
  }
}
