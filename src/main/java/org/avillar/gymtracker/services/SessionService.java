package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.Session;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SessionService {

    List<Session> getAllProgramSessions(Long programId);

    Session getProgramSession(Long programId, Long sessionId);

    Session createSession(Long programId, Session session);

    Session updateSession(Long programId, Long sessionId, Session session);

    void deleteSession(Long programId, Long sessionId);

}
