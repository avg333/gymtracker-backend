package org.avillar.gymtracker.setgroup.application;

import org.avillar.gymtracker.errors.application.EntityNotFoundException;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupDto;

import java.util.List;

public interface SetGroupService {

    List<SetGroupDto> getAllSessionSetGroups(Long programId) throws EntityNotFoundException, IllegalAccessException;

    List<SetGroupDto> getAllWorkoutSetGroups(Long workoutId) throws EntityNotFoundException, IllegalAccessException;

    SetGroupDto getLastTimeUserExerciseSetGroup(Long userId, Long exerciseId) throws IllegalAccessException;

    SetGroupDto getSetGroup(Long setGroupId) throws EntityNotFoundException, IllegalAccessException;

    SetGroupDto createSetGroupInSession(SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException;

    SetGroupDto createSetGroupInWorkout(SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException;

    SetGroupDto replaceSetGroupSetsFromSetGroup(Long setGroupDestinationId, Long setGroupSourceId) throws IllegalAccessException;

    SetGroupDto updateSetGroup(SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException;

    void deleteSetGroup(Long setGroupId) throws EntityNotFoundException, IllegalAccessException;
}
