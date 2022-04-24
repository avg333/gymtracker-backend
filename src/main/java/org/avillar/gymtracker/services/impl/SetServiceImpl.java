package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.SetRepository;
import org.avillar.gymtracker.model.entities.Session;
import org.avillar.gymtracker.model.entities.Set;
import org.avillar.gymtracker.services.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetServiceImpl implements SetService {

    private final SetRepository setRepository;

    @Autowired

    public SetServiceImpl(SetRepository setRepository) {
        this.setRepository = setRepository;
    }

    @Override
    public List<Set> getAllSessionSets(Long programId, Long sessionId) {
        return this.setRepository.findAll();
    }

    @Override
    public Set getSessionSet(Long programId, Long sessionId, Long setId) {
        return this.setRepository.findById(setId).orElse(null);
    }

    @Override
    public Set createSet(Long programId, Long sessionId, Set set) {
        final Session session = new Session();
        session.setId(sessionId);
        set.setSession(session);
        return this.setRepository.save(set);
    }

    @Override
    public Set updateSet(Long programId, Long sessionId, Long setId, Set set) {
        final Session session = new Session();
        session.setId(sessionId);
        set.setSession(session);
        set.setId(setId);
        return this.setRepository.save(set);
    }

    @Override
    public void deleteSet(Long programId, Long sessionId, Long setId) {
        this.setRepository.deleteById(setId);
    }
}
