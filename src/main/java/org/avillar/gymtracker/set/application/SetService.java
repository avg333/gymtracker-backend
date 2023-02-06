package org.avillar.gymtracker.set.application;

import org.avillar.gymtracker.errors.application.BadFormException;
import org.avillar.gymtracker.errors.application.EntityNotFoundException;
import org.avillar.gymtracker.errors.application.IllegalAccessException;
import org.avillar.gymtracker.set.application.dto.SetDto;

import java.util.List;

public interface SetService {

    List<SetDto> getAllSetGroupSets(Long setGroupId) throws EntityNotFoundException, IllegalAccessException;

    SetDto getSet(Long setId) throws EntityNotFoundException, IllegalAccessException;

    SetDto getSetDefaultDataForNewSet(Long setGroupId, Integer setNumber) throws EntityNotFoundException, IllegalAccessException;

    SetDto createSet(SetDto setDto) throws EntityNotFoundException, IllegalAccessException, BadFormException;

    SetDto updateSet(SetDto setDto) throws EntityNotFoundException, IllegalAccessException, BadFormException;

    void deleteSet(Long setId) throws EntityNotFoundException, IllegalAccessException;

}
