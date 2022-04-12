package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.entities.Set;

import java.util.List;

public interface SetService {

    List<Set> getAllSessionSets(Long programId, Long sessionId);

    Set getSessionSet(Long programId, Long sessionId, Long setId);

    Set createSet(Long programId, Long sessionId, Set set);

    Set updateSet(Long programId, Long sessionId, Long setId, Set set);

    void deleteSet(Long programId, Long sessionId, Long setId);
}
