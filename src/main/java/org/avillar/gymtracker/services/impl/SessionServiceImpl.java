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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                for (final org.avillar.gymtracker.model.entities.Set set : setGroup.getSets()) {
                    exerciseIds.add(set.getExercise().getId());
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
        return this.modelMapper.map(this.sessionRepository.findById(sessionId).orElse(null), SessionDto.class);
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
        if (session.getSessionOrder() == null || session.getSessionOrder() > sessions.size()) {
            session.setSessionOrder(sessions.size());
            this.sessionRepository.save(session);
        } else {
            for (final Session sessionExistent : sessions) {
                if (sessionExistent.getSessionOrder() >= session.getSessionOrder()) {
                    sessionExistent.setSessionOrder(sessionExistent.getSessionOrder() + 1);
                }
            }
            sessions.add(session);
            this.sessionRepository.saveAll(sessions);
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
        final Integer positionPreUpdate = sessionDb.getSessionOrder();

        this.programService.programExistsAndIsFromLoggedUser(sessionDb.getProgram().getId());
        final Session session = this.modelMapper.map(sessionDto, Session.class);
        final Program program = new Program();
        program.setId(sessionDb.getProgram().getId());
        session.setProgram(program);
        //TODO Deberia actualizarse sin pisar los valores viejos de usuario y sesiones


        return this.modelMapper.map(this.sessionRepository.save(session), SessionDto.class);
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

        // Reorder sessions
        final List<Session> sessions = this.sessionRepository.findByProgramOrderBySessionOrder(session.getProgram());

        if (!sessions.isEmpty()) {
            for (final Session sessionExistent : sessions) {
                if (sessionExistent.getSessionOrder() > sessionPosition) {
                    sessionExistent.setSessionOrder(sessionExistent.getSessionOrder() - 1);
                }
            }
            this.sessionRepository.saveAll(sessions);
        }
    }

    private void reorderSessions(){

    }

}
