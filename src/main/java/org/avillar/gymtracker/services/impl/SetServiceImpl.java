package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.SetGroupRepository;
import org.avillar.gymtracker.model.dao.SetRepository;
import org.avillar.gymtracker.model.dto.SetDto;
import org.avillar.gymtracker.model.entities.Set;
import org.avillar.gymtracker.model.entities.SetGroup;
import org.avillar.gymtracker.services.LoginService;
import org.avillar.gymtracker.services.SetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

@Service
public class SetServiceImpl implements SetService {
    private static final String NOT_FOUND_PARENT_ERROR_MSG = "El grupo de series no existe";
    private static final String NOT_FOUND_ERROR_MSG = "La serie no existe";
    private final SetRepository setRepository;
    private final SetGroupRepository setGroupRepository;
    private final ModelMapper modelMapper;
    private final LoginService loginService;

    @Autowired
    public SetServiceImpl(SetRepository setRepository, SetGroupRepository setGroupRepository,
                          LoginService loginService, ModelMapper modelMapper) {
        this.setRepository = setRepository;
        this.setGroupRepository = setGroupRepository;
        this.modelMapper = modelMapper;
        this.loginService = loginService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SetDto> getAllSetGroupSets(final Long setGroupId) throws IllegalAccessException {
        final SetGroup setGroup = this.setGroupRepository.findById(setGroupId).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_PARENT_ERROR_MSG));
        this.loginService.checkAccess(setGroup.getSession().getProgram());
        final List<Set> sets = this.setRepository.findBySetGroupOrderByListOrderAsc(setGroup);
        return sets.stream().map(set -> this.modelMapper.map(set, SetDto.class)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SetDto getSet(final Long setId) throws IllegalAccessException {
        final Set set = this.setRepository.findById(setId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(set.getSetGroup().getSession().getProgram());
        return this.modelMapper.map(set, SetDto.class);
    }

    @Override
    @Transactional
    public SetDto createSet(final SetDto setDto) throws IllegalAccessException {
        setDto.setId(null);
        final SetGroup setGroup = this.setGroupRepository.findById(setDto.getSetGroupId()).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_PARENT_ERROR_MSG));
        this.loginService.checkAccess(setGroup.getSession().getProgram());

        final Set set = this.modelMapper.map(setDto, Set.class);
        final List<Set> sets = this.setRepository.findBySetGroupOrderByListOrderAsc(setGroup);
        if (set.getListOrder() == null || set.getListOrder() > sets.size() || set.getListOrder() < 0) {
            set.setListOrder(sets.size());
            this.setRepository.save(set);
        } else {
            this.setRepository.save(set);
            this.reorderAllSetGroupsSetsAfterPost(setGroup, set);
        }

        return this.modelMapper.map(set, SetDto.class);
    }

    @Override
    @Transactional
    public SetDto updateSet(final SetDto setDto) throws IllegalAccessException {
        final Set setDb = this.setRepository.findById(setDto.getId()).orElseThrow(()
                -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(setDb.getSetGroup().getSession().getProgram());
        final SetGroup setGroup = this.setGroupRepository.findById(setDto.getSetGroupId()).orElseThrow(()
                -> new EntityNotFoundException(NOT_FOUND_PARENT_ERROR_MSG));
        this.loginService.checkAccess(setGroup.getSession().getProgram());

        final Set set = this.modelMapper.map(setDto, Set.class);

        final int oldPosition = set.getListOrder();
        this.setRepository.save(set);
        this.reorderAllSetGroupsSetsAfterUpdate(set.getSetGroup(), set, oldPosition);

        return this.modelMapper.map(set, SetDto.class);
    }

    @Override
    @Transactional
    public void deleteSet(final Long setGroupId) throws IllegalAccessException {
        final Set set = this.setRepository.findById(setGroupId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(set.getSetGroup().getSession().getProgram());

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