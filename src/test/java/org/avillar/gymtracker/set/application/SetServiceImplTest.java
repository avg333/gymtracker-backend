package org.avillar.gymtracker.set.application;

import org.avillar.gymtracker.auth.application.AuthService;
import org.avillar.gymtracker.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.set.application.dto.SetDtoValidator;
import org.avillar.gymtracker.set.application.dto.SetMapper;
import org.avillar.gymtracker.set.domain.Set;
import org.avillar.gymtracker.set.domain.SetDao;
import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.avillar.gymtracker.setgroup.domain.SetGroupDao;
import org.avillar.gymtracker.sort.application.EntitySorter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class SetServiceImplTest {

    @Mock
    private SetDao setDao;
    @Mock
    private SetGroupDao setGroupDao;
    @Mock
    private SetMapper setMapper;
    @Mock
    private EntitySorter entitySorter;
    @Mock
    private SetDtoValidator setDtoValidator;
    @Mock
    private AuthService authService;

    @InjectMocks
    private SetServiceImpl setService;

    @Test
    void getAllSetGroupSets() {
    }

    @Test
    void getSet() {
    }

    @Test
    void getSetDefaultDataForNewSet() {
    }

    @Test
    void createSet() {
    }

    @Test
    void updateSet() {
    }

    @Test
    void deleteSetWhenNotExists() {
        final Long setId = 12L;
        given(setDao.findById(anyLong())).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> setService.deleteSet(setId));
    }

    @Test
    void deleteSetWhenNotPermission() throws IllegalAccessException {
        final Long setId = 12L;
        final Set set = new Set();
        final SetGroup setGroup = new SetGroup();
        set.setSetGroup(setGroup);

        given(setDao.findById(anyLong())).willReturn(Optional.of(set));
        doThrow(IllegalAccessException.class).when(authService).checkAccess(setGroup);

        assertThrows(IllegalAccessException.class, () -> setService.deleteSet(setId));
    }

    @Test
    void deleteSetOk() throws IllegalAccessException {
        final Long setId = 12L;

        final Set set = new Set();
        final SetGroup setGroup = new SetGroup();
        set.setSetGroup(setGroup);

        given(setDao.findById(anyLong())).willReturn(Optional.of(set));
        doNothing().when(authService).checkAccess(set.getSetGroup());
        doNothing().when(entitySorter).sortDelete(anySet(), eq(set));

        assertDoesNotThrow(() -> setService.deleteSet(setId));
    }
}