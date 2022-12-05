package org.avillar.gymtracker.set.application;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.base.application.BaseService;
import org.avillar.gymtracker.set.application.dto.SetDto;
import org.avillar.gymtracker.set.application.dto.SetMapper;
import org.avillar.gymtracker.set.application.dto.SetValidator;
import org.avillar.gymtracker.set.domain.Set;
import org.avillar.gymtracker.set.domain.SetDao;
import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.avillar.gymtracker.setgroup.domain.SetGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class SetServiceImpl extends BaseService implements SetService {
    private static final String SET_GROUP_NOT_FOUND_ERROR_MSG = "The setGroup does not exist";
    private static final String SET_NOT_FOUND_ERROR_MSG = "The set does not exist";
    private final SetDao setDao;
    private final SetGroupDao setGroupDao;
    private final SetMapper setMapper;
    private final SetValidator setValidator;

    @Autowired
    public SetServiceImpl(SetDao setDao, SetGroupDao setGroupDao, SetMapper setMapper, SetValidator setValidator) {
        this.setDao = setDao;
        this.setGroupDao = setGroupDao;
        this.setMapper = setMapper;
        this.setValidator = setValidator;
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<SetDto> getAllSetGroupSets(final Long setGroupId) throws EntityNotFoundException, IllegalAccessException {
        final SetGroup setGroup = this.setGroupDao.findById(setGroupId)
                .orElseThrow(() -> new EntityNotFoundException(SET_GROUP_NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(setGroup);
        return this.setMapper.toDtos(this.setDao.findBySetGroupOrderByListOrderAsc(setGroup), true);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public SetDto getSet(final Long setId) throws EntityNotFoundException, IllegalAccessException {
        final Set set = this.setDao.findById(setId)
                .orElseThrow(() -> new EntityNotFoundException(SET_NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(set.getSetGroup());
        return this.setMapper.toDto(set, true);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public SetDto createSet(final SetDto setDto) throws IllegalAccessException {
        if (!this.setValidator.validate(setDto, new HashMap<>()).isEmpty()) {
            throw new RuntimeException("El set esta mal formado");
        }// TODO Mejorar devolucion de errores

        final SetGroup setGroup = this.setGroupDao.getReferenceById(setDto.getSetGroup().getId());

        final Set set = this.setMapper.toEntity(setDto);
        this.authService.checkAccess(set.getSetGroup());

        final int setsSize = this.setDao.findBySetGroupOrderByListOrderAsc(setGroup).size();
        if (null == set.getListOrder() || set.getListOrder() > setsSize || 0 > set.getListOrder()) {
            set.setListOrder(setsSize);
            this.setDao.save(set);
        } else {
            this.setDao.save(set);
            this.reorderAllSetGroupsSetsAfterPost(setGroup, set);
        }

        return this.setMapper.toDto(set, true);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public SetDto updateSet(final SetDto setDto) throws IllegalAccessException {
        if (!this.setValidator.validate(setDto, new HashMap<>()).isEmpty()) {
            throw new RuntimeException("El set esta mal formado");
        }// TODO Mejorar devolucion de errores


        final Set setDb = this.setDao.getReferenceById(setDto.getId());
        this.authService.checkAccess(setDb.getSetGroup());

        final Set set = this.setMapper.toEntity(setDto);
        set.setSetGroup(setDb.getSetGroup());

        final int oldPosition = set.getListOrder();
        this.setDao.save(set);
        this.reorderAllSetGroupsSetsAfterUpdate(set.getSetGroup(), set, oldPosition);

        return this.setMapper.toDto(set, true);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteSet(final Long setId) throws EntityNotFoundException, IllegalAccessException {
        final Set set = this.setDao.findById(setId)
                .orElseThrow(() -> new EntityNotFoundException(SET_NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(set.getSetGroup());

        final int setGroupPosition = set.getListOrder();
        this.setDao.deleteById(setId);

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