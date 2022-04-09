package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.Set;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SetService {

    List<Set> getAllSessionSets(Long programId, Long sessionId);

    Set getSessionSet(Long programId, Long sessionId, Long setId);

    Set addSet(Long programId, Long sessionId, Set set);

    Set updateSet(Long programId, Long sessionId, Long setId, Set set);

    void deleteSet(Long programId, Long sessionId, Long setId);
}
