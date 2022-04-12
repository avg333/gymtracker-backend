package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.entities.Session;

import java.util.List;

public interface SessionService {

    List<Session> getAllProgramSessions(Long programId);

    Session getProgramSession(Long programId, Long sessionId);

    Session createSession(Long programId, Session session);

    Session updateSession(Long programId, Long sessionId, Session session);

    void deleteSession(Long programId, Long sessionId);

}
