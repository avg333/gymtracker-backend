package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.dto.SetGroupDto;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface SetGroupService {

    List<SetGroupDto> getAllSessionSetGroups(Long programId) throws IllegalAccessException;

    @Transactional(readOnly = true)
    List<SetGroupDto> getAllWorkoutSetGroups(Long workoutId) throws IllegalAccessException;

    SetGroupDto getSetGroup(Long setGroupId) throws EntityNotFoundException, IllegalAccessException;

    SetGroupDto createSetGroup(SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException;

    SetGroupDto updateSetGroup(SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException;

    void deleteSetGroup(Long setGroupId) throws EntityNotFoundException, IllegalAccessException;

}
