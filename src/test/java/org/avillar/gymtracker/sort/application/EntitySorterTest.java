package org.avillar.gymtracker.sort.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class EntitySorterTest {

    @Test
    void isValidNewListOrder() {
        // The listOrder must be between [0, setsSize]

        // Empty list and negative list order
        assertFalse(() -> EntitySorter.isValidNewListOrder(-1, 0));
        assertFalse(() -> EntitySorter.isValidNewListOrder(-5, 0));

        // Empty list and positive list order
        assertFalse(() -> EntitySorter.isValidNewListOrder(1, 0));
        assertFalse(() -> EntitySorter.isValidNewListOrder(5, 0));

        // Not empty list and negative list order
        assertFalse(() -> EntitySorter.isValidNewListOrder(-1, 1));
        assertFalse(() -> EntitySorter.isValidNewListOrder(-5, 5));

        // Not empty list and no list order
        assertTrue(() -> EntitySorter.isValidNewListOrder(0, 1));
        assertTrue(() -> EntitySorter.isValidNewListOrder(0, 5));

        // Not empty list and list order over max items
        assertFalse(() -> EntitySorter.isValidNewListOrder(2, 1));
        assertFalse(() -> EntitySorter.isValidNewListOrder(7, 5));

        // Empty list and no list order
        assertTrue(() -> EntitySorter.isValidNewListOrder(0, 0));

        assertTrue(() -> EntitySorter.isValidNewListOrder(3, 3));
    }
}