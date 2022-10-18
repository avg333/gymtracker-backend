package org.avillar.gymtracker.utils;

import org.avillar.gymtracker.model.entities.SortableEntity;

import java.util.List;
import java.util.Objects;

public class EntityOrder {

    private EntityOrder() {
    }

    public static void reorderAfterDelete(final List<SortableEntity> sortableEntities, final int oldPosition) {
        if (sortableEntities.isEmpty()) {
            return;
        }

        for (final SortableEntity sortableEntity : sortableEntities) {
            if (sortableEntity.getListOrder() > oldPosition) {
                sortableEntity.setListOrder(sortableEntity.getListOrder() - 1);
            }
        }
    }

    public static void reorderAfterUpdate(final List<SortableEntity> sortableEntities, final Long entityId, final int newPosition, int oldPosition) {
        if (sortableEntities.isEmpty() || newPosition == oldPosition) {
            return;
        }

        final int diferencia = oldPosition > newPosition ? 1 : -1;
        for (final SortableEntity sortableEntity : sortableEntities) {
            if (entityId.equals(sortableEntity.getId())) {
                break;
            }
            final boolean esMismaPosicion = Objects.equals(newPosition, sortableEntity.getListOrder());
            final boolean menorQueMaximo = sortableEntity.getListOrder() < Math.max(oldPosition, newPosition);
            final boolean mayorQueMinimo = sortableEntity.getListOrder() > Math.min(oldPosition, newPosition);
            if (esMismaPosicion || (menorQueMaximo && mayorQueMinimo)) {
                sortableEntity.setListOrder(sortableEntity.getListOrder() + diferencia);
            }
        }
    }

    public static void reorderAfterPost(final List<SortableEntity> sortableEntities, final Long entityId, final int newPosition) {
        if (sortableEntities.isEmpty()) {
            return;
        }

        for (final SortableEntity sortableEntity : sortableEntities) {
            if (!entityId.equals(sortableEntity.getId()) && newPosition <= sortableEntity.getListOrder()) {
                sortableEntity.setListOrder(sortableEntity.getListOrder() + 1);
            }
        }
    }
}
