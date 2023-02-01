package org.avillar.gymtracker.set.application;

import org.avillar.gymtracker.base.application.BaseService;
import org.avillar.gymtracker.errors.application.EntityNotFoundException;
import org.avillar.gymtracker.errors.application.IllegalAccessException;
import org.avillar.gymtracker.set.application.dto.SetDto;
import org.avillar.gymtracker.set.application.dto.SetMapper;
import org.avillar.gymtracker.set.application.dto.SetValidator;
import org.avillar.gymtracker.set.domain.Set;
import org.avillar.gymtracker.set.domain.SetDao;
import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.avillar.gymtracker.setgroup.domain.SetGroupDao;
import org.avillar.gymtracker.sort.application.EntitySorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class SetServiceImpl extends BaseService implements SetService {

    private final SetDao setDao;
    private final SetGroupDao setGroupDao;
    private final SetMapper setMapper;
    private final SetValidator setValidator;
    private final EntitySorter entitySorter;

    @Autowired
    public SetServiceImpl(SetDao setDao, SetGroupDao setGroupDao, SetMapper setMapper, SetValidator setValidator,
                          EntitySorter entitySorter) {
        this.setDao = setDao;
        this.setGroupDao = setGroupDao;
        this.setMapper = setMapper;
        this.setValidator = setValidator;
        this.entitySorter = entitySorter;
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public List<SetDto> getAllSetGroupSets(final Long setGroupId) throws EntityNotFoundException, IllegalAccessException {
        final SetGroup setGroup = this.setGroupDao.findById(setGroupId)
                .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId));
        this.authService.checkAccess(setGroup);
        return this.setMapper.toDtos(this.setDao.findBySetGroupOrderByListOrderAsc(setGroup), -1);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public SetDto getSet(final Long setId) throws EntityNotFoundException, IllegalAccessException {
        final Set set = this.setDao.findById(setId)
                .orElseThrow(() -> new EntityNotFoundException(Set.class, setId));
        this.authService.checkAccess(set.getSetGroup());
        return this.setMapper.toDto(set, -1);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public SetDto getSetDefaultDataForNewSet(Long setGroupId) throws EntityNotFoundException, IllegalAccessException {
        final SetGroup setGroup = this.setGroupDao.findById(setGroupId)
                .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId));
        this.authService.checkAccess(setGroup);

        final List<Set> sets = this.setDao.findLastSetForExerciseAndUser(this.authService.getLoggedUser(), setGroup.getExercise(), setGroup);
        if (!sets.isEmpty()) {
            return this.setMapper.toDto(sets.get(0), -1);
        }//TODO Mejorar esto
    //FIXME Deberia devolver resultados solo sobre el ultimos setGroup
        final List<Set> setsAux = this.setDao.findLastSetForExerciseAndUserAux(setGroup);
        if (!setsAux.isEmpty()) {
            return this.setMapper.toDto(setsAux.get(0), -1);
        }

        return new SetDto();
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
        }

        this.setDao.save(set);

        final java.util.Set<Set> sets = set.getSetGroup().getSets();
        if (this.entitySorter.sortPost(sets, set)) {
            this.setDao.saveAll(sets);
        }

        return this.setMapper.toDto(set, -1);
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

        final java.util.Set<Set> sets = set.getSetGroup().getSets();
        if (this.entitySorter.sortUpdate(sets, set, oldPosition)) {
            this.setDao.saveAll(sets);
        }

        return this.setMapper.toDto(set, -1);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteSet(final Long setId) throws EntityNotFoundException, IllegalAccessException {
        final Set set = this.setDao.findById(setId)
                .orElseThrow(() -> new EntityNotFoundException(Set.class, setId));
        this.authService.checkAccess(set.getSetGroup());

        this.setDao.deleteById(setId);

        final java.util.Set<Set> sets = set.getSetGroup().getSets();
        if (this.entitySorter.sortDelete(sets, set)) {
            this.setDao.saveAll(sets);
        }
    }

}
