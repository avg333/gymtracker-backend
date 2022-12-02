package org.avillar.gymtracker.set.application;

import java.util.List;

public interface SetService {

    List<SetDto> getAllSetGroupSets(Long setGroupId) throws IllegalAccessException;

    SetDto getSet(Long setId) throws IllegalAccessException;

    SetDto createSet(SetDto setDto) throws IllegalAccessException;

    SetDto updateSet(SetDto setDto) throws IllegalAccessException;

    void deleteSet(Long setGroupId) throws IllegalAccessException;
}