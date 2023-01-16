package org.avillar.gymtracker.setgroup.application;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SetGroupService {

    List<SetGroupDto> getAllSessionSetGroups(Long programId) throws EntityNotFoundException, IllegalAccessException;

    @Transactional(readOnly = true)
    List<SetGroupDto> getAllWorkoutSetGroups(Long workoutId) throws EntityNotFoundException, IllegalAccessException;

    SetGroupDto getSetGroup(Long setGroupId) throws EntityNotFoundException, IllegalAccessException;

    SetGroupDto createSetGroupInSession(SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException;

    SetGroupDto createSetGroupInWorkout(SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException;

    SetGroupDto updateSetGroup(SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException;

    void deleteSetGroup(Long setGroupId) throws EntityNotFoundException, IllegalAccessException;

    SetGroupDto getLastTimeUserExerciseSetGroup(Long userId, Long exerciseId) throws IllegalAccessException;
}
