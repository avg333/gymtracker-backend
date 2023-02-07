package org.avillar.gymtracker.setgroup.application;

import org.avillar.gymtracker.errors.application.exceptions.BadFormException;
import org.avillar.gymtracker.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupDto;

import java.util.List;

public interface SetGroupService {

    List<SetGroupDto> getAllSessionSetGroups(Long programId) throws EntityNotFoundException, IllegalAccessException;

    List<SetGroupDto> getAllWorkoutSetGroups(Long workoutId) throws EntityNotFoundException, IllegalAccessException;

    SetGroupDto getSetGroup(Long setGroupId) throws EntityNotFoundException, IllegalAccessException;

    SetGroupDto createSetGroupInSession(SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException, BadFormException;

    SetGroupDto createSetGroupInWorkout(SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException, BadFormException;

    SetGroupDto replaceSetGroupSetsFromSetGroup(Long setGroupDestinationId, Long setGroupSourceId) throws IllegalAccessException;

    SetGroupDto updateSetGroup(SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException, BadFormException;

    void deleteSetGroup(Long setGroupId) throws EntityNotFoundException, IllegalAccessException;
}
