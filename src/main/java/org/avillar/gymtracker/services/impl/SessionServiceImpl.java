package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.ProgramRepository;
import org.avillar.gymtracker.model.dao.SessionRepository;
import org.avillar.gymtracker.model.dto.SessionDto;
import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.model.entities.Session;
import org.avillar.gymtracker.model.entities.SetGroup;
import org.avillar.gymtracker.services.LoginService;
import org.avillar.gymtracker.services.SessionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class SessionServiceImpl implements SessionService {
    private static final String NOT_FOUND_PARENT_ERROR_MSG = "El programa no existe";
    private static final String NOT_FOUND_ERROR_MSG = "La sesión no existe";

    private final SessionRepository sessionRepository;
    private final ProgramRepository programRepository;
    private final ModelMapper modelMapper;
    private final LoginService loginService;

    @Autowired
    public SessionServiceImpl(ProgramRepository programRepository, SessionRepository sessionRepository,
                              LoginService loginService, ModelMapper modelMapper) {
        this.sessionRepository = sessionRepository;
        this.modelMapper = modelMapper;
        this.programRepository = programRepository;
        this.loginService = loginService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<SessionDto> getAllProgramSessionsWithData(final Long programId) throws IllegalAccessException {
        final Program program = this.programRepository.findById(programId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_PARENT_ERROR_MSG));
        this.loginService.checkAccess(program);
        final List<Session> sessions = this.sessionRepository.findByProgramOrderByListOrder(program);
        return this.getSessionsDtosWithVolume(sessions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SessionDto> getAllNotProgramsLoggedUserSessionsWithData() {
        final List<Session> sessions = this.sessionRepository.findByUserAppAndProgramIsNullOrderByDateDesc(this.loginService.getLoggedUser());
        return this.getSessionsDtosWithVolume(sessions);
    }

    private List<SessionDto> getSessionsDtosWithVolume(List<Session> sessions) {
        final List<SessionDto> sessionDtos = new ArrayList<>(sessions.size());
        for (final Session session : sessions) {
            final SessionDto sessionDto = this.modelMapper.map(session, SessionDto.class);
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
        final Session session = this.sessionRepository.findById(sessionId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(session);
        return this.modelMapper.map(session, SessionDto.class);
    }

    @Override
    public List<SessionDto> getSessionByDate(Date date) throws EntityNotFoundException {
        final List<Session> sessions = this.sessionRepository.findByDateAndUserAppAndProgramIsNullOrderByDateDesc(date,this.loginService.getLoggedUser());
        return this.getSessionsDtosWithVolume(sessions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public SessionDto createSessionInProgram(final SessionDto sessionDto) throws EntityNotFoundException, IllegalAccessException {
        final Program program = this.programRepository.findById(sessionDto.getProgramId()).orElseThrow(()
                -> new EntityNotFoundException(NOT_FOUND_PARENT_ERROR_MSG));
        this.loginService.checkAccess(program);
        final Session session = this.modelMapper.map(sessionDto, Session.class);
        session.setDate(null);
        session.setUserApp(this.loginService.getLoggedUser());

        final int sessionsSize = this.sessionRepository.findByProgramOrderByListOrder(program).size();
        if (session.getListOrder() == null || session.getListOrder() > sessionsSize || session.getListOrder() < 0) {
            session.setListOrder(sessionsSize);
            this.sessionRepository.save(session);
        } else {
            this.sessionRepository.save(session);
            this.reorderAllProgramSessionsAfterPost(program, session);
        }

        return this.modelMapper.map(session, SessionDto.class);
    }

    @Override
    @Transactional
    public SessionDto createSession(final SessionDto sessionDto) throws EntityNotFoundException {
        final Session session = this.modelMapper.map(sessionDto, Session.class);
        session.setUserApp(this.loginService.getLoggedUser());
        session.setListOrder(null);
        return this.modelMapper.map(this.sessionRepository.save(session), SessionDto.class);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public SessionDto updateProgramSession(final SessionDto sessionDto) throws EntityNotFoundException, IllegalAccessException {
        final Session sessionDb = this.sessionRepository.findById(sessionDto.getId()).orElseThrow(()
                -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(sessionDb.getProgram());
        final Program program = this.programRepository.findById(sessionDto.getProgramId()).orElseThrow(()
                -> new EntityNotFoundException(NOT_FOUND_PARENT_ERROR_MSG));
        this.loginService.checkAccess(program);
        final Session session = this.modelMapper.map(sessionDto, Session.class);
        this.loginService.checkAccess(sessionDb);
        session.setDate(null);
        session.setUserApp(this.loginService.getLoggedUser());

        final int oldPosition = sessionDb.getListOrder();
        this.sessionRepository.save(session);
        this.reorderAllProgramSessionsAfterUpdate(sessionDb.getProgram(), session, oldPosition);

        return this.modelMapper.map(session, SessionDto.class);
    }

    @Override
    @Transactional
    public SessionDto updateSession(final SessionDto sessionDto) throws EntityNotFoundException, IllegalAccessException {
        final Session sessionDb = this.sessionRepository.findById(sessionDto.getId()).orElseThrow(()
                -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(sessionDb);
        final Session session = this.modelMapper.map(sessionDto, Session.class);
        session.setProgram(null);
        session.setListOrder(null);
        session.setUserApp(this.loginService.getLoggedUser());

        return this.modelMapper.map(this.sessionRepository.save(session), SessionDto.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteProgramSession(final Long sessionId) throws EntityNotFoundException, IllegalAccessException {
        final Session session = this.sessionRepository.findById(sessionId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(session);

        final Integer sessionPosition = session.getListOrder();
        this.sessionRepository.deleteById(sessionId);

        this.reorderAllProgramSessionsAfterDelete(session.getProgram(), sessionPosition);
    }

    @Override
    @Transactional
    public void deleteSession(final Long sessionId) throws EntityNotFoundException, IllegalAccessException {
        final Session session = this.sessionRepository.findById(sessionId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        if(session.getProgram() != null){
            this.deleteProgramSession(sessionId);
        } else {
            this.loginService.checkAccess(session);
            this.sessionRepository.deleteById(sessionId);
        }
    }

    private void reorderAllProgramSessionsAfterDelete(final Program program, final int sessionPosition) {
        final List<Session> sessions = this.sessionRepository.findByProgramOrderByListOrder(program);
        if (sessions.isEmpty()) {
            return;
        }

        for (final Session sessionExistent : sessions) {
            if (sessionExistent.getListOrder() > sessionPosition) {
                sessionExistent.setListOrder(sessionExistent.getListOrder() - 1);
            }
        }

        this.sessionRepository.saveAll(sessions);
    }

    private void reorderAllProgramSessionsAfterUpdate(final Program program, final Session newSession, int oldPosition) {
        final List<Session> sessions = this.sessionRepository.findByProgramOrderByListOrder(program);
        if (sessions.isEmpty()) {
            return;
        }

        final int newPosition = newSession.getListOrder();
        if (newPosition == oldPosition) {
            return;
        }

        final int diferencia = oldPosition > newPosition ? 1 : -1;
        for (final Session sessionExistent : sessions) {
            if (!newSession.getId().equals(sessionExistent.getId())) {
                final boolean esMismaPosicion = Objects.equals(newSession.getListOrder(), sessionExistent.getListOrder());
                final boolean menorQueMaximo = sessionExistent.getListOrder() < Math.max(oldPosition, newPosition);
                final boolean mayorQueMinimo = sessionExistent.getListOrder() > Math.min(oldPosition, newPosition);
                if (esMismaPosicion || (menorQueMaximo && mayorQueMinimo)) {
                    sessionExistent.setListOrder(sessionExistent.getListOrder() + diferencia);
                }
            }
        }

        this.sessionRepository.saveAll(sessions);
    }

    private void reorderAllProgramSessionsAfterPost(final Program program, final Session newSession) {
        final List<Session> sessions = this.sessionRepository.findByProgramOrderByListOrder(program);
        if (sessions.isEmpty()) {
            return;
        }

        for (final Session sessionExistent : sessions) {
            if (!newSession.getId().equals(sessionExistent.getId()) &&
                    newSession.getListOrder() <= sessionExistent.getListOrder()) {
                sessionExistent.setListOrder(sessionExistent.getListOrder() + 1);
            }
        }

        this.sessionRepository.saveAll(sessions);
    }

}
