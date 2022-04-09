package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.dao.SessionRepository;
import org.avillar.gymtracker.model.Program;
import org.avillar.gymtracker.model.Session;
import org.avillar.gymtracker.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public List<Session> getAllProgramSessions(Long programId) {
        return this.sessionRepository.findAll();
    }

    @Override
    public Session getProgramSession(Long programId, Long sessionId) {
        return this.sessionRepository.findById(sessionId).orElse(null);
    }

    @Override
    public Session createSession(Long programId, Session session) {
        final Program program = new Program();
        program.setId(programId);
        session.setProgram(program);
        return this.sessionRepository.save(session);
    }

    @Override
    public Session updateSession(Long programId, Long sessionId, Session session) {
        final Program program = new Program();
        program.setId(programId);
        session.setProgram(program);
        session.setId(sessionId);
        return this.sessionRepository.save(session);
    }

    @Override
    public void deleteSession(Long programId, Long sessionId) {
        this.sessionRepository.deleteById(sessionId);
    }
}
