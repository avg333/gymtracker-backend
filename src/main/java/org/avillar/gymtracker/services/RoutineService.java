package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.Program;
import org.avillar.gymtracker.model.Session;
import org.avillar.gymtracker.model.Set;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoutineService {

    public List<Program> getAllPrograms();

    public Program getProgramById(Long id);

    public Program createProgram(Program program);

    public Program updateProgram(Program program);

    public void deleteProgram(Program program);

    public List<Session> getSessionsByProgram(Program program);

    public Session getSessionById(Long id);

    public Session addSessionToProgram(Session session, Program program);

    public Session updateSession(Session session, Program program);

    public void deleteSession(Session session);

    public List<Set> getSetsBySession(Session session);

    public Set getSetById(Long id);

    public Set addSetToSession(Set set, Session session);

    public Set updateSet(Set set, Session session);

    public void deleteSet(Set set);
}
