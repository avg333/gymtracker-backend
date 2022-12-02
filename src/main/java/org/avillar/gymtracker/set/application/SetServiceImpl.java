package org.avillar.gymtracker.set.application;

import org.avillar.gymtracker.base.application.BaseService;
import org.avillar.gymtracker.set.domain.Set;
import org.avillar.gymtracker.set.domain.SetDao;
import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.avillar.gymtracker.setgroup.domain.SetGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

@Service
public class SetServiceImpl extends BaseService implements SetService {
    private static final String NOT_FOUND_PARENT_ERROR_MSG = "El grupo de series no existe";
    private static final String NOT_FOUND_ERROR_MSG = "La serie no existe";
    private final SetDao setDao;
    private final SetGroupDao setGroupDao;

    @Autowired
    public SetServiceImpl(SetDao setDao, SetGroupDao setGroupDao) {
        this.setDao = setDao;
        this.setGroupDao = setGroupDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SetDto> getAllSetGroupSets(final Long setGroupId) throws IllegalAccessException {
        final SetGroup setGroup = this.setGroupDao.findById(setGroupId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_PARENT_ERROR_MSG));
        this.authService.checkAccess(setGroup);
        final List<Set> sets = this.setDao.findBySetGroupOrderByListOrderAsc(setGroup);
        return sets.stream().map(set -> this.modelMapper.map(set, SetDto.class)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SetDto getSet(final Long setId) throws IllegalAccessException {
        final Set set = this.setDao.findById(setId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(set.getSetGroup());
        return this.modelMapper.map(set, SetDto.class);
    }

    @Override
    @Transactional
    public SetDto createSet(final SetDto setDto) throws IllegalAccessException {
        final SetGroup setGroup = this.setGroupDao.findById(setDto.getSetGroupId()).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_PARENT_ERROR_MSG));
        this.authService.checkAccess(setGroup);

        final Set set = this.modelMapper.map(setDto, Set.class);
        final List<Set> sets = this.setDao.findBySetGroupOrderByListOrderAsc(setGroup);
        if (null == set.getListOrder() || set.getListOrder() > sets.size() || 0 > set.getListOrder()) {
            set.setListOrder(sets.size());
            this.setDao.save(set);
        } else {
            this.setDao.save(set);
            this.reorderAllSetGroupsSetsAfterPost(setGroup, set);
        }

        return this.modelMapper.map(set, SetDto.class);
    }

    @Override
    @Transactional
    public SetDto updateSet(final SetDto setDto) throws IllegalAccessException {
        final Set setDb = this.setDao.findById(setDto.getId()).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(setDb.getSetGroup());
        final SetGroup setGroup = this.setGroupDao.findById(setDto.getSetGroupId()).orElseThrow(()
                -> new EntityNotFoundException(NOT_FOUND_PARENT_ERROR_MSG));
        this.authService.checkAccess(setGroup);

        final Set set = this.modelMapper.map(setDto, Set.class);

        final int oldPosition = set.getListOrder();
        this.setDao.save(set);
        this.reorderAllSetGroupsSetsAfterUpdate(set.getSetGroup(), set, oldPosition);

        return this.modelMapper.map(set, SetDto.class);
    }

    @Override
    @Transactional
    public void deleteSet(final Long setGroupId) throws IllegalAccessException {
        final Set set = this.setDao.findById(setGroupId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(set.getSetGroup());

        final int setGroupPosition = set.getListOrder();
        this.setDao.deleteById(setGroupId);

        this.reorderAllSetGroupsSetsAfterDelete(set.getSetGroup(), setGroupPosition);
    }

    private void reorderAllSetGroupsSetsAfterDelete(final SetGroup setGroup, final int sessionPosition) {
        final List<Set> sets = this.setDao.findBySetGroupOrderByListOrderAsc(setGroup);
        if (sets.isEmpty()) {
            return;
        }

        for (final Set set : sets) {
            if (set.getListOrder() > sessionPosition) {
                set.setListOrder(set.getListOrder() - 1);
            }
        }

        this.setDao.saveAll(sets);

    }

    private void reorderAllSetGroupsSetsAfterUpdate(final SetGroup setGroup, final Set newSet, int oldPosition) {
        final List<Set> sets = this.setDao.findBySetGroupOrderByListOrderAsc(setGroup);
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

        this.setDao.saveAll(sets);
    }

    private void reorderAllSetGroupsSetsAfterPost(final SetGroup setGroup, final Set newSet) {
        final List<Set> sets = this.setDao.findBySetGroupOrderByListOrderAsc(setGroup);
        if (sets.isEmpty()) {
            return;
        }

        for (final Set set : sets) {
            if (!newSet.getId().equals(set.getId()) &&
                    newSet.getListOrder() <= set.getListOrder()) {
                set.setListOrder(set.getListOrder() + 1);
            }
        }

        this.setDao.saveAll(sets);
    }
}