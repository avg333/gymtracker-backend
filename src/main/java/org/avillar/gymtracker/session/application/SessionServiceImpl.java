package org.avillar.gymtracker.session.application;

import org.avillar.gymtracker.base.application.BaseService;
import org.avillar.gymtracker.errors.application.exceptions.BadFormException;
import org.avillar.gymtracker.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.program.domain.Program;
import org.avillar.gymtracker.program.domain.ProgramDao;
import org.avillar.gymtracker.session.application.dto.SessionDto;
import org.avillar.gymtracker.session.application.dto.SessionDtoValidator;
import org.avillar.gymtracker.session.application.dto.SessionMapper;
import org.avillar.gymtracker.session.domain.Session;
import org.avillar.gymtracker.session.domain.SessionDao;
import org.avillar.gymtracker.sort.application.EntitySorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.DataBinder;

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

    /**
     * @ {@inheritDoc}
     */
    @Override
    public List<SessionDto> getAllProgramSessions(final Long programId) throws EntityNotFoundException, IllegalAccessException {
        final Program program = this.programDao.findById(programId)
                .orElseThrow(() -> new EntityNotFoundException(Program.class, programId));
        this.authService.checkAccess(program);

        return this.sessionMapper.toDtos(this.sessionDao.findByProgramOrderByListOrder(program), true);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public SessionDto getSession(final Long sessionId) throws EntityNotFoundException, IllegalAccessException {
        final Session session = this.sessionDao.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException(Session.class, sessionId));
        this.authService.checkAccess(session.getProgram());
        return this.sessionMapper.toDto(session, true);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public SessionDto createSession(final SessionDto sessionDto) throws EntityNotFoundException, BadFormException {
        final DataBinder dataBinder = new DataBinder(sessionDto);
        dataBinder.addValidators(new SessionDtoValidator());
        dataBinder.validate();
        if (dataBinder.getBindingResult().hasErrors()) {
            throw new BadFormException(SessionDto.class, dataBinder.getBindingResult());
        }

        final Program program = this.programDao.getReferenceById(sessionDto.getProgramDto().getId());
        final Session session = this.sessionMapper.toEntity(sessionDto);

        final int sessionsSize = this.sessionDao.findByProgramOrderByListOrder(program).size();
        if (null == session.getListOrder() || session.getListOrder() > sessionsSize || 0 > session.getListOrder()) {
            session.setListOrder(sessionsSize);
        }

        this.sessionDao.save(session);

        final Set<Session> sessions = session.getProgram().getSessions();
        this.entitySorter.sortPost(sessions, session);
        if (!CollectionUtils.isEmpty(sessions)) {
            this.sessionDao.saveAll(sessions);
        }

        return this.sessionMapper.toDto(session, true);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public SessionDto updateSession(final SessionDto sessionDto) throws EntityNotFoundException, BadFormException {
        final DataBinder dataBinder = new DataBinder(sessionDto);
        dataBinder.addValidators(new SessionDtoValidator());
        dataBinder.validate();
        if (dataBinder.getBindingResult().hasErrors()) {
            throw new BadFormException(SessionDto.class, dataBinder.getBindingResult());
        }

        final Session sessionDb = this.sessionDao.getReferenceById(sessionDto.getId());

        final Session session = this.sessionMapper.toEntity(sessionDto);
        session.setProgram(sessionDb.getProgram());

        final int oldPosition = sessionDb.getListOrder();
        this.sessionDao.save(session);

        final Set<Session> sessions = session.getProgram().getSessions();
        this.entitySorter.sortUpdate(sessions, session, oldPosition);
        if (!CollectionUtils.isEmpty(sessions)) {
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
        this.entitySorter.sortDelete(sessions, session);
        if (!CollectionUtils.isEmpty(sessions)) {
            this.sessionDao.saveAll(sessions);
        }
    }

}
