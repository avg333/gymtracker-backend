package org.avillar.gymtracker.setgroup.application;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SetGroupService {

    List<SetGroupDto> getAllSessionSetGroups(Long programId) throws IllegalAccessException;

    @Transactional(readOnly = true)
    List<SetGroupDto> getAllWorkoutSetGroups(Long workoutId) throws IllegalAccessException;

    SetGroupDto getSetGroup(Long setGroupId) throws EntityNotFoundException, IllegalAccessException;

    SetGroupDto createSetGroupInSession(SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException;

    @Transactional
    SetGroupDto createSetGroupInWorkout(SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException;

    SetGroupDto updateSetGroup(SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException;

    void deleteSetGroup(Long setGroupId) throws EntityNotFoundException, IllegalAccessException;

}
