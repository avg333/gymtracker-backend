package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.SessionRepository;
import org.avillar.gymtracker.model.dto.SessionDto;
import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.model.entities.Session;
import org.avillar.gymtracker.model.entities.SetGroup;
import org.avillar.gymtracker.services.ProgramService;
import org.avillar.gymtracker.services.SessionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class SessionServiceImpl implements SessionService {
    private static final String NOT_FOUND_ERROR_MSG = "La sesión no existe";

    private final ProgramService programService;
    private final SessionRepository sessionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, ProgramService programService, ModelMapper modelMapper) {
        this.sessionRepository = sessionRepository;
        this.modelMapper = modelMapper;
        this.programService = programService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<SessionDto> getAllProgramSessionsWithData(final Long programId) throws IllegalAccessException {
        this.programService.programExistsAndIsFromLoggedUser(programId);
        final Program program = new Program();
        program.setId(programId);
        final List<Session> sessions = this.sessionRepository.findByProgramOrderBySessionOrder(program);
        final List<SessionDto> sessionDtos = new ArrayList<>(sessions.size());

        for (final Session session : sessions) {
            SessionDto sessionDto = this.modelMapper.map(session, SessionDto.class);
            final Set<Long> exerciseIds = new HashSet<>();
            int sets = 0;

            for (final SetGroup setGroup : session.getSetGroups()) {
                exerciseIds.add(setGroup.getExercise().getId());
                for (final org.avillar.gymtracker.model.entities.Set set : setGroup.getSets()) {
                    if (set.getRir() <= 3) {
                        sets++;
                    }
                }
            }

            sessionDto.setExercisesNumber(exerciseIds.size());
            sessionDto.setSetsNumber(sets);
            sessionDtos.add(sessionDto);
        }

        return sessionDtos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public SessionDto getSession(final Long sessionId) throws EntityNotFoundException, IllegalAccessException {
        final Session session = this.sessionRepository.findById(sessionId).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.programService.programExistsAndIsFromLoggedUser(session.getProgram().getId());
        return this.modelMapper.map(session, SessionDto.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public SessionDto createSessionInProgram(final SessionDto sessionDto) throws EntityNotFoundException, IllegalAccessException {
        this.programService.programExistsAndIsFromLoggedUser(sessionDto.getIdProgram());
        final Session session = this.modelMapper.map(sessionDto, Session.class);
        final Program program = new Program();
        program.setId(sessionDto.getIdProgram());
        session.setProgram(program);

        final List<Session> sessions = this.sessionRepository.findByProgramOrderBySessionOrder(program);
        if (session.getSessionOrder() == null || session.getSessionOrder() > sessions.size() || session.getSessionOrder() < 0) {
            session.setSessionOrder(sessions.size());
            this.sessionRepository.save(session);
        } else {
            this.sessionRepository.save(session);
            this.reorderAllProgramSessionsAfterPost(program, session);
        }

        return this.modelMapper.map(session, SessionDto.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public SessionDto updateSession(final SessionDto sessionDto) throws EntityNotFoundException, IllegalAccessException {
        if (sessionDto.getId() == null) {
            throw new EntityNotFoundException(NOT_FOUND_ERROR_MSG);
        }
        final Session sessionDb = this.sessionRepository.findById(sessionDto.getId()).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.programService.programExistsAndIsFromLoggedUser(sessionDb.getProgram().getId());
        final Session session = this.modelMapper.map(sessionDto, Session.class);

        final int oldPosition = sessionDb.getSessionOrder();
        this.sessionRepository.save(session);
        this.reorderAllProgramSessionsAfterUpdate(sessionDb.getProgram(), session, oldPosition);

        return this.modelMapper.map(session, SessionDto.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteSession(final Long sessionId) throws EntityNotFoundException, IllegalAccessException {
        final Session session = this.sessionRepository.findById(sessionId).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.programService.programExistsAndIsFromLoggedUser(session.getProgram().getId());

        final Integer sessionPosition = session.getSessionOrder();
        this.sessionRepository.deleteById(sessionId);

        this.reorderAllProgramSessionsAfterDelete(session.getProgram(), sessionPosition);
    }

    private void reorderAllProgramSessionsAfterDelete(final Program program, final int sessionPosition) {
        final List<Session> sessions = this.sessionRepository.findByProgramOrderBySessionOrder(program);
        if (sessions.isEmpty()) {
            return;
        }

        for (final Session sessionExistent : sessions) {
            if (sessionExistent.getSessionOrder() > sessionPosition) {
                sessionExistent.setSessionOrder(sessionExistent.getSessionOrder() - 1);
            }
        }

        this.sessionRepository.saveAll(sessions);
    }

    private void reorderAllProgramSessionsAfterUpdate(final Program program, final Session newSession, int oldPosition) {
        final List<Session> sessions = this.sessionRepository.findByProgramOrderBySessionOrder(program);
        if (sessions.isEmpty()) {
            return;
        }

        final int newPosition = newSession.getSessionOrder();
        if(newPosition == oldPosition){
            return;
        }

        final int diferencia = oldPosition > newPosition ? 1 : -1;
        for (final Session sessionExistent : sessions) {
            if (!newSession.getId().equals(sessionExistent.getId())) {
                final boolean esMismaPosicion = Objects.equals(newSession.getSessionOrder(), sessionExistent.getSessionOrder());
                final boolean menorQueMaximo = sessionExistent.getSessionOrder() < Math.max(oldPosition, newPosition);
                final boolean mayorQueMinimo = sessionExistent.getSessionOrder() > Math.min(oldPosition, newPosition);
                if (esMismaPosicion || (menorQueMaximo && mayorQueMinimo)) {
                    sessionExistent.setSessionOrder(sessionExistent.getSessionOrder() + diferencia);
                }
            }
        }

        this.sessionRepository.saveAll(sessions);
    }

    private void reorderAllProgramSessionsAfterPost(final Program program, final Session newSession) {
        final List<Session> sessions = this.sessionRepository.findByProgramOrderBySessionOrder(program);
        if (sessions.isEmpty()) {
            return;
        }

        for (final Session sessionExistent : sessions) {
            if (!newSession.getId().equals(sessionExistent.getId()) &&
                    newSession.getSessionOrder() <= sessionExistent.getSessionOrder()) {
                sessionExistent.setSessionOrder(sessionExistent.getSessionOrder() + 1);
            }
        }

        this.sessionRepository.saveAll(sessions);
    }

}
