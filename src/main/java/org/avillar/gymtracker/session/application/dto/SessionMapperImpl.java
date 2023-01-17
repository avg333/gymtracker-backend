package org.avillar.gymtracker.session.application.dto;

import org.avillar.gymtracker.program.application.ProgramDto;
import org.avillar.gymtracker.program.domain.Program;
import org.avillar.gymtracker.session.domain.Session;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Component
public class SessionMapperImpl implements SessionMapper {

    private final SetGroupMapper setGroupMapper;

    public SessionMapperImpl(SetGroupMapper setGroupMapper) {
        this.setGroupMapper = setGroupMapper;
    }

    @Override
    public List<SessionDto> toDtos(Collection<Session> sessions, boolean nested) {
        if (CollectionUtils.isEmpty(sessions)) {
            return Collections.emptyList();
        }

        return sessions.stream().map(session -> this.toDto(session, nested)).toList();
    }

    @Override
    public SessionDto toDto(Session session, boolean nested) {
        if (session == null) {
            return null;
        }

        final SessionDto sessionDto = new SessionDto();
        sessionDto.setId(session.getId());
        sessionDto.setName(session.getName());
        sessionDto.setDescription(session.getDescription());
        sessionDto.setDayOfWeek(session.getDayOfWeek());
        sessionDto.setListOrder(session.getListOrder());

        if (session.getProgram() != null && nested) {
            final ProgramDto programDto = new ProgramDto();
            programDto.setId(session.getProgram().getId());
            sessionDto.setProgramDto(programDto);
        }

        if (session.getSetGroups() != null && nested) {
            sessionDto.setSetGroups(this.setGroupMapper.toDtos(session.getSetGroups(), false));
        }

        return sessionDto;
    }

    @Override
    public Session toEntity(SessionDto sessionDto) {
        if (sessionDto == null) {
            return null;
        }

        final Session session = new Session();
        session.setId(sessionDto.getId());
        session.setName(sessionDto.getName());
        session.setDescription(sessionDto.getDescription());
        session.setDayOfWeek(sessionDto.getDayOfWeek());
        session.setListOrder(sessionDto.getListOrder());

        if (sessionDto.getProgramDto() != null) {
            final Program program = new Program();
            program.setId(sessionDto.getProgramDto().getId());
            session.setProgram(program);
        }

        if (sessionDto.getSetGroups() != null) {
            session.setSetGroups(new HashSet<>(this.setGroupMapper.toEntities(sessionDto.getSetGroups())));
        }

        return session;
    }
}
