package org.avillar.gymtracker.session.application;

import org.avillar.gymtracker.base.application.BaseService;
import org.avillar.gymtracker.errors.application.EntityNotFoundException;
import org.avillar.gymtracker.program.domain.Program;
import org.avillar.gymtracker.program.domain.ProgramDao;
import org.avillar.gymtracker.session.application.dto.SessionDto;
import org.avillar.gymtracker.session.application.dto.SessionMapper;
import org.avillar.gymtracker.session.domain.Session;
import org.avillar.gymtracker.session.domain.SessionDao;
import org.avillar.gymtracker.sort.application.EntitySorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class SessionServiceImpl extends BaseService implements SessionService {

    private final SessionDao sessionDao;
    private final ProgramDao programDao;
    private final EntitySorter entitySorter;
    private final SessionMapper sessionMapper;

    @Autowired
    public SessionServiceImpl(ProgramDao programDao, SessionDao sessionDao, EntitySorter entitySorter,
                              SessionMapper sessionMapper) {
        this.sessionDao = sessionDao;
        this.programDao = programDao;
        this.entitySorter = entitySorter;

        this.sessionMapper = sessionMapper;
    }

    @Override
    public List<SessionDto> getAllProgramSessions(final Long programId) throws EntityNotFoundException, IllegalAccessException {
        final Program program = this.programDao.findById(programId)
                .orElseThrow(() -> new EntityNotFoundException(Program.class, programId));
        this.authService.checkAccess(program);

        return this.sessionMapper.toDtos(this.sessionDao.findByProgramOrderByListOrder(program), true);
    }

    @Override
    public SessionDto getSession(final Long sessionId) throws EntityNotFoundException, IllegalAccessException {
        final Session session = this.sessionDao.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException(Session.class, sessionId));
        this.authService.checkAccess(session.getProgram());
        return this.sessionMapper.toDto(session, true);
    }

    @Override
    @Transactional
    public SessionDto createSession(final SessionDto sessionDto) throws EntityNotFoundException, IllegalAccessException {
        //TODO Validar sessionDto
        final Program program = this.programDao.findById(sessionDto.getProgramDto().getId())
                .orElseThrow(() -> new EntityNotFoundException(Program.class, sessionDto.getProgramDto().getId()));
        this.authService.checkAccess(program);
        final Session session = this.sessionMapper.toEntity(sessionDto);

        final int sessionsSize = this.sessionDao.findByProgramOrderByListOrder(program).size();
        if (null == session.getListOrder() || session.getListOrder() > sessionsSize || 0 > session.getListOrder()) {
            session.setListOrder(sessionsSize);
        }

        this.sessionDao.save(session);

        final Set<Session> sessions = session.getProgram().getSessions();
        if (this.entitySorter.sortPost(sessions, session)) {
            this.sessionDao.saveAll(sessions);
        }

        return this.sessionMapper.toDto(session, true);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public SessionDto updateSession(final SessionDto sessionDto) throws EntityNotFoundException, IllegalAccessException {
        //TODO Validar sessionDto
        final Session sessionDb = this.sessionDao.findById(sessionDto.getId()).orElseThrow(()
                -> new EntityNotFoundException(Session.class, sessionDto.getId()));
        this.authService.checkAccess(sessionDb.getProgram());
        final Program program = this.programDao.findById(sessionDto.getProgramDto().getId()).orElseThrow(()
                -> new EntityNotFoundException(Program.class, sessionDto.getProgramDto().getId()));

        this.authService.checkAccess(program);
        final Session session = this.sessionMapper.toEntity(sessionDto);
        this.authService.checkAccess(sessionDb.getProgram());

        final int oldPosition = sessionDb.getListOrder();
        this.sessionDao.save(session);

        final Set<Session> sessions = session.getProgram().getSessions();
        if (this.entitySorter.sortUpdate(sessions, session, oldPosition)) {
            this.sessionDao.saveAll(sessions);
        }

        return this.sessionMapper.toDto(session, true);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteSession(final Long sessionId) throws EntityNotFoundException, IllegalAccessException {
        final Session session = this.sessionDao.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException(Session.class, sessionId));
        this.authService.checkAccess(session.getProgram());

        this.sessionDao.deleteById(sessionId);

        final Set<Session> sessions = session.getProgram().getSessions();
        if (this.entitySorter.sortDelete(sessions, session)) {
            this.sessionDao.saveAll(sessions);
        }
    }

}
