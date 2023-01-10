package org.avillar.gymtracker.utils.application;

import org.avillar.gymtracker.base.domain.SortableEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

@Component
public class EntitySorter {

    public <T extends SortableEntity> boolean sortDelete(final Set<? extends SortableEntity> entities, final T entity) {
        boolean changes = false;
        entities.removeIf(e -> e.getId().equals(entity.getId()));

        for (final SortableEntity sortableEntity : entities) {
            if (sortableEntity.getListOrder() > entity.getListOrder()) {
                sortableEntity.setListOrder(sortableEntity.getListOrder() - 1);
                changes = true;
            }
        }

        return changes;
    }

    public <T extends SortableEntity> boolean sortPost(final Set<? extends SortableEntity> entities, final T entity) {
        boolean changes = false;
        entities.removeIf(e -> e.getId().equals(entity.getId()));

        for (final SortableEntity sortableEntity : entities) {
            if (entity.getListOrder() <= sortableEntity.getListOrder()) {
                sortableEntity.setListOrder(sortableEntity.getListOrder() + 1);
            }
        }

        return changes;
    }

    public <T extends SortableEntity> boolean sortUpdate(final Set<? extends SortableEntity> entities, final T entity, final int oldPosition) {
        final int newPosition = entity.getListOrder();
        if (newPosition == oldPosition) {
            return false;
        }

        boolean changes = false;
        entities.removeIf(e -> e.getId().equals(entity.getId()));

        final int diferencia = oldPosition > newPosition ? 1 : -1;
        for (final SortableEntity sortableEntity : entities) {
            final boolean esMismaPosicion = Objects.equals(entity.getListOrder(), sortableEntity.getListOrder());
            final boolean menorQueMaximo = sortableEntity.getListOrder() < Math.max(oldPosition, newPosition);
            final boolean mayorQueMinimo = sortableEntity.getListOrder() > Math.min(oldPosition, newPosition);
            if (esMismaPosicion || (menorQueMaximo && mayorQueMinimo)) {
                sortableEntity.setListOrder(sortableEntity.getListOrder() + diferencia);
                changes = true;
            }
        }

        return changes;
    }

}
