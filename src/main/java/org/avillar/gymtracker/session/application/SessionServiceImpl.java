package org.avillar.gymtracker.session.application;

import org.avillar.gymtracker.base.application.BaseService;
import org.avillar.gymtracker.program.domain.Program;
import org.avillar.gymtracker.program.domain.ProgramDao;
import org.avillar.gymtracker.session.domain.Session;
import org.avillar.gymtracker.session.domain.SessionDao;
import org.avillar.gymtracker.utils.application.VolumeCalculatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

@Service
public class SessionServiceImpl extends BaseService implements SessionService {
    private static final String NOT_FOUND_PARENT_ERROR_MSG = "El programa no existe";
    private static final String NOT_FOUND_ERROR_MSG = "La sesión no existe";

    private final SessionDao sessionDao;
    private final ProgramDao programDao;
    private final VolumeCalculatorUtils volumeCalculatorUtils;

    @Autowired
    public SessionServiceImpl(ProgramDao programDao, SessionDao sessionDao, VolumeCalculatorUtils volumeCalculatorUtils) {
        this.sessionDao = sessionDao;
        this.programDao = programDao;
        this.volumeCalculatorUtils = volumeCalculatorUtils;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SessionDto> getAllProgramSessions(final Long programId) throws IllegalAccessException {
        final Program program = this.programDao.findById(programId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_PARENT_ERROR_MSG));
        this.authService.checkAccess(program);
        final List<Session> sessions = this.sessionDao.findByProgramOrderByListOrder(program);
        return sessions.stream().map(this.volumeCalculatorUtils::calculateSessionVolume).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SessionDto getSession(final Long sessionId) throws EntityNotFoundException, IllegalAccessException {
        final Session session = this.sessionDao.findById(sessionId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(session.getProgram());
        return this.modelMapper.map(session, SessionDto.class);
    }

    @Override
    @Transactional
    public SessionDto createSession(final SessionDto sessionDto) throws EntityNotFoundException, IllegalAccessException {
        //TODO Validar sessionDto
        final Program program = this.programDao.findById(sessionDto.getProgramId()).orElseThrow(()
                -> new EntityNotFoundException(NOT_FOUND_PARENT_ERROR_MSG));
        this.authService.checkAccess(program);
        final Session session = this.modelMapper.map(sessionDto, Session.class);

        final int sessionsSize = this.sessionDao.findByProgramOrderByListOrder(program).size();
        if (null == session.getListOrder() || session.getListOrder() > sessionsSize || 0 > session.getListOrder()) {
            session.setListOrder(sessionsSize);
            this.sessionDao.save(session);
        } else {
            this.sessionDao.save(session);
            this.reorderAllProgramSessionsAfterPost(program, session);
        }

        return this.modelMapper.map(session, SessionDto.class);
    }


    @Override
    @Transactional
    public SessionDto updateSession(final SessionDto sessionDto) throws EntityNotFoundException, IllegalAccessException {
        //TODO Validar sessionDto
        final Session sessionDb = this.sessionDao.findById(sessionDto.getId()).orElseThrow(()
                -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(sessionDb.getProgram());
        final Program program = this.programDao.findById(sessionDto.getProgramId()).orElseThrow(()
                -> new EntityNotFoundException(NOT_FOUND_PARENT_ERROR_MSG));
        this.authService.checkAccess(program);
        final Session session = this.modelMapper.map(sessionDto, Session.class);
        this.authService.checkAccess(sessionDb.getProgram());
        // TODO Repasar logica si se puede cambiar de programa

        final int oldPosition = sessionDb.getListOrder();
        this.sessionDao.save(session);
        this.reorderAllProgramSessionsAfterUpdate(sessionDb.getProgram(), session, oldPosition);

        return this.modelMapper.map(session, SessionDto.class);
    }

    @Override
    @Transactional
    public void deleteProgram(final Long sessionId) throws EntityNotFoundException, IllegalAccessException {
        final Session session = this.sessionDao.findById(sessionId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(session.getProgram());

        final int sessionPosition = session.getListOrder();
        this.sessionDao.deleteById(sessionId);

        this.reorderAllProgramSessionsAfterDelete(session.getProgram(), sessionPosition);
    }

    // ----- Funciones de ordenación -----

    private void reorderAllProgramSessionsAfterDelete(final Program program, final int sessionPosition) {
        final List<Session> sessions = this.sessionDao.findByProgramOrderByListOrder(program);
        if (sessions.isEmpty() || sessions.size() == sessionPosition) {
            return;
        }

        for (final Session sessionExistent : sessions) {
            if (sessionExistent.getListOrder() > sessionPosition) {
                sessionExistent.setListOrder(sessionExistent.getListOrder() - 1);
            }
        }

        this.sessionDao.saveAll(sessions);
    }

    private void reorderAllProgramSessionsAfterUpdate(final Program program, final Session newSession, int oldPosition) {
        final List<Session> sessions = this.sessionDao.findByProgramOrderByListOrder(program);
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

        this.sessionDao.saveAll(sessions);
    }

    private void reorderAllProgramSessionsAfterPost(final Program program, final Session newSession) {
        final List<Session> sessions = this.sessionDao.findByProgramOrderByListOrder(program);
        if (sessions.isEmpty()) {
            return;
        }

        for (final Session sessionExistent : sessions) {
            if (!newSession.getId().equals(sessionExistent.getId()) &&
                    newSession.getListOrder() <= sessionExistent.getListOrder()) {
                sessionExistent.setListOrder(sessionExistent.getListOrder() + 1);
            }
        }

        this.sessionDao.saveAll(sessions);
    }

}
