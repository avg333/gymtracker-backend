package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.SetGroupRepository;
import org.avillar.gymtracker.model.dto.SessionDto;
import org.avillar.gymtracker.model.dto.SetGroupDto;
import org.avillar.gymtracker.model.entities.Session;
import org.avillar.gymtracker.model.entities.SetGroup;
import org.avillar.gymtracker.services.ProgramService;
import org.avillar.gymtracker.services.SessionService;
import org.avillar.gymtracker.services.SetGroupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SetGroupServiceImpl implements SetGroupService {
    private static final String NOT_FOUND_ERROR_MSG = "El SetGroup no existe";

    private final SetGroupRepository setGroupRepository;
    private final ProgramService programService;
    private final SessionService sessionService;
    private final ModelMapper modelMapper;

    @Autowired
    public SetGroupServiceImpl(SetGroupRepository setGroupRepository, ProgramService programService, SessionService sessionService, ModelMapper modelMapper) {
        this.programService = programService;
        this.setGroupRepository = setGroupRepository;
        this.sessionService = sessionService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SetGroupDto> getAllSessionSetGroups(Long sessionId) throws IllegalAccessException {
        final SessionDto sessionDto = this.sessionService.getSession(sessionId);
        final Session session = this.modelMapper.map(sessionDto, Session.class);
        final List<SetGroup> setGroups = this.setGroupRepository.findBySessionOrderByListOrderAsc(session);
        final List<SetGroupDto> setGroupDtos = new ArrayList<>(setGroups.size());

        for (final SetGroup setGroup : setGroups) {
            setGroupDtos.add(this.modelMapper.map(setGroup, SetGroupDto.class));
        }

        return setGroupDtos;
    }

    @Override
    public SetGroupDto getSetGroup(Long setGroupId) throws EntityNotFoundException, IllegalAccessException {
        final SetGroup setGroup = this.setGroupRepository.findById(setGroupId).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.programService.programExistsAndIsFromLoggedUser(setGroup.getSession().getProgram().getId());
        return this.modelMapper.map(setGroup, SetGroupDto.class);
    }

    @Override
    public SetGroupDto createSetGroup(SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException {
        final SessionDto sessionDto = this.sessionService.getSession(setGroupDto.getIdSession());
        final Session session = this.modelMapper.map(sessionDto, Session.class);

        final SetGroup setGroup = this.modelMapper.map(setGroupDto, SetGroup.class);
        final List<SetGroup> setGroups = this.setGroupRepository.findBySessionOrderByListOrderAsc(session);
        if (setGroup.getListOrder() == null || setGroup.getListOrder() > setGroups.size() || setGroup.getListOrder() < 0) {
            setGroup.setListOrder(setGroups.size());
            this.setGroupRepository.save(setGroup);
        } else {
            this.setGroupRepository.save(setGroup);
            this.reorderAllSessionSetGroupsAfterPost(session, setGroup);
        }

        return this.modelMapper.map(setGroup, SetGroupDto.class);
    }

    @Override
    public SetGroupDto updateSetGroup(SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException {
        if (setGroupDto.getId() == null) {
            throw new EntityNotFoundException(NOT_FOUND_ERROR_MSG);
        }
        final SetGroup setGroupDb = this.setGroupRepository.findById(setGroupDto.getId()).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.programService.programExistsAndIsFromLoggedUser(setGroupDb.getSession().getProgram().getId());
        final SetGroup setGroup = this.modelMapper.map(setGroupDto, SetGroup.class);

        final int oldPosition = setGroupDb.getListOrder();
        this.setGroupRepository.save(setGroup);
        this.reorderAllSessionSetGroupsAfterUpdate(setGroup.getSession(), setGroup, oldPosition);

        return this.modelMapper.map(setGroup, SetGroupDto.class);
    }

    @Override
    public void deleteSetGroup(Long setGroupId) throws EntityNotFoundException, IllegalAccessException {
        final SetGroup setGroup = this.setGroupRepository.findById(setGroupId).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.programService.programExistsAndIsFromLoggedUser(setGroup.getSession().getProgram().getId());

        final int setGroupPosition = setGroup.getListOrder();
        this.setGroupRepository.deleteById(setGroupId);

        this.reorderAllSessionSetGroupsAfterDelete(setGroup.getSession(), setGroupPosition);
    }


    private void reorderAllSessionSetGroupsAfterDelete(final Session session, final int sessionPosition) {
        final List<SetGroup> setGroups = this.setGroupRepository.findBySessionOrderByListOrderAsc(session);
        if (setGroups.isEmpty()) {
            return;
        }

        for (final SetGroup setGroup : setGroups) {
            if (setGroup.getListOrder() > sessionPosition) {
                setGroup.setListOrder(setGroup.getListOrder() - 1);
            }
        }

        this.setGroupRepository.saveAll(setGroups);
    }


    private void reorderAllSessionSetGroupsAfterUpdate(final Session session, final SetGroup newSetGroup, int oldPosition) {
        final List<SetGroup> setGroups = this.setGroupRepository.findBySessionOrderByListOrderAsc(session);
        if (setGroups.isEmpty()) {
            return;
        }

        final int newPosition = newSetGroup.getListOrder();
        if (newPosition == oldPosition) {
            return;
        }

        final int diferencia = oldPosition > newPosition ? 1 : -1;
        for (final SetGroup setGroup : setGroups) {
            if (!newSetGroup.getId().equals(setGroup.getId())) {
                final boolean esMismaPosicion = Objects.equals(newSetGroup.getListOrder(), setGroup.getListOrder());
                final boolean menorQueMaximo = setGroup.getListOrder() < Math.max(oldPosition, newPosition);
                final boolean mayorQueMinimo = setGroup.getListOrder() > Math.min(oldPosition, newPosition);
                if (esMismaPosicion || (menorQueMaximo && mayorQueMinimo)) {
                    setGroup.setListOrder(setGroup.getListOrder() + diferencia);
                }
            }
        }

        this.setGroupRepository.saveAll(setGroups);
    }


    private void reorderAllSessionSetGroupsAfterPost(final Session session, final SetGroup newSetGroup) {
        final List<SetGroup> setGroups = this.setGroupRepository.findBySessionOrderByListOrderAsc(session);
        if (setGroups.isEmpty()) {
            return;
        }

        for (final SetGroup setGroup : setGroups) {
            if (!newSetGroup.getId().equals(setGroup.getId()) &&
                    newSetGroup.getListOrder() <= setGroup.getListOrder()) {
                setGroup.setListOrder(setGroup.getListOrder() + 1);
            }
        }

        this.setGroupRepository.saveAll(setGroups);
    }

}
