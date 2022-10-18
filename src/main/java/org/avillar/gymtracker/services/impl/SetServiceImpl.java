package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.SetRepository;
import org.avillar.gymtracker.model.dto.SetDto;
import org.avillar.gymtracker.model.dto.SetGroupDto;
import org.avillar.gymtracker.model.entities.Set;
import org.avillar.gymtracker.model.entities.SetGroup;
import org.avillar.gymtracker.services.ProgramService;
import org.avillar.gymtracker.services.SetGroupService;
import org.avillar.gymtracker.services.SetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SetServiceImpl implements SetService {
    private static final String NOT_FOUND_ERROR_MSG = "La serie no existe";
    private final ProgramService programService;
    private final SetRepository setRepository;
    private final SetGroupService setGroupService;
    private final ModelMapper modelMapper;

    @Autowired
    public SetServiceImpl(SetRepository setRepository, ProgramService programService, SetGroupService setGroupService, ModelMapper modelMapper) {
        this.programService = programService;
        this.setRepository = setRepository;
        this.modelMapper = modelMapper;
        this.setGroupService = setGroupService;
    }


    @Override
    public List<SetDto> getAllSetGroupSets(final Long setGroupId) throws IllegalAccessException {
        final SetGroupDto setGroupDto = this.setGroupService.getSetGroup(setGroupId);
        final SetGroup setGroup = this.modelMapper.map(setGroupDto, SetGroup.class);
        final List<Set> sets = this.setRepository.findBySetGroupOrderByListOrderAsc(setGroup);
        final List<SetDto> setDtos = new ArrayList<>(sets.size());

        for (final Set set : sets) {
            setDtos.add(this.modelMapper.map(set, SetDto.class));
        }

        return setDtos;
    }

    @Override
    public SetDto getSet(final Long setId) throws IllegalAccessException {
        final Set set = this.setRepository.findById(setId).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.programService.programExistsAndIsFromLoggedUser(set.getSetGroup().getSession().getProgram().getId());
        return this.modelMapper.map(set, SetDto.class);
    }

    @Override
    public SetDto createSet(SetDto setDto) throws IllegalAccessException {
        final SetGroupDto setGroupDto = this.setGroupService.getSetGroup(setDto.getSetGroupId());
        final SetGroup setGroup = this.modelMapper.map(setGroupDto, SetGroup.class);

        final Set set = this.modelMapper.map(setDto, Set.class);
        final List<Set> sets = this.setRepository.findBySetGroupOrderByListOrderAsc(setGroup);
        if (set.getListOrder() == null || set.getListOrder() > sets.size() || set.getListOrder() < 0) {
            setGroup.setListOrder(sets.size());
            this.setRepository.save(set);
        } else {
            this.setRepository.save(set);
            this.reorderAllSetGroupsSetsAfterPost(setGroup, set);
        }

        return this.modelMapper.map(set, SetDto.class);
    }

    @Override
    public SetDto updateSet(SetDto setDto) throws IllegalAccessException {
        if (setDto.getId() == null) {
            throw new EntityNotFoundException(NOT_FOUND_ERROR_MSG);
        }
        final Set setDb = this.setRepository.findById(setDto.getId()).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.programService.programExistsAndIsFromLoggedUser(setDb.getSetGroup().getSession().getProgram().getId());
        final Set set = this.modelMapper.map(setDto, Set.class);

        final int oldPosition = set.getListOrder();
        this.setRepository.save(set);
        this.reorderAllSetGroupsSetsAfterUpdate(set.getSetGroup(), set, oldPosition);

        return this.modelMapper.map(set, SetDto.class);
    }

    @Override
    public void deleteSet(Long setGroupId) throws IllegalAccessException {
        final Set set = this.setRepository.findById(setGroupId).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.programService.programExistsAndIsFromLoggedUser(set.getSetGroup().getSession().getProgram().getId());

        final int setGroupPosition = set.getListOrder();
        this.setRepository.deleteById(setGroupId);

        this.reorderAllSetGroupsSetsAfterDelete(set.getSetGroup(), setGroupPosition);
    }

    private void reorderAllSetGroupsSetsAfterDelete(final SetGroup setGroup, final int sessionPosition) {
        final List<Set> sets = this.setRepository.findBySetGroupOrderByListOrderAsc(setGroup);
        if (sets.isEmpty()) {
            return;
        }

        for (final Set set : sets) {
            if (set.getListOrder() > sessionPosition) {
                set.setListOrder(set.getListOrder() - 1);
            }
        }

        this.setRepository.saveAll(sets);

    }

    private void reorderAllSetGroupsSetsAfterUpdate(final SetGroup setGroup, final Set newSet, int oldPosition) {
        final List<Set> sets = this.setRepository.findBySetGroupOrderByListOrderAsc(setGroup);
        if (sets.isEmpty()) {
            return;
        }

        final int newPosition = newSet.getListOrder();
        if (newPosition == oldPosition) {
            return;
        }

        final int diferencia = oldPosition > newPosition ? 1 : -1;
        for (final Set set : sets) {
            if (!newSet.getId().equals(set.getId())) {
                final boolean esMismaPosicion = Objects.equals(newSet.getListOrder(), set.getListOrder());
                final boolean menorQueMaximo = set.getListOrder() < Math.max(oldPosition, newPosition);
                final boolean mayorQueMinimo = set.getListOrder() > Math.min(oldPosition, newPosition);
                if (esMismaPosicion || (menorQueMaximo && mayorQueMinimo)) {
                    set.setListOrder(set.getListOrder() + diferencia);
                }
            }
        }

        this.setRepository.saveAll(sets);
    }


    private void reorderAllSetGroupsSetsAfterPost(final SetGroup setGroup, final Set newSet) {
        final List<Set> sets = this.setRepository.findBySetGroupOrderByListOrderAsc(setGroup);
        if (sets.isEmpty()) {
            return;
        }

        for (final Set set : sets) {
            if (!newSet.getId().equals(set.getId()) &&
                    newSet.getListOrder() <= set.getListOrder()) {
                set.setListOrder(set.getListOrder() + 1);
            }
        }

        this.setRepository.saveAll(sets);
    }
}
