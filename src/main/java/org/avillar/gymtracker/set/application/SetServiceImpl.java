package org.avillar.gymtracker.set.application;

import org.avillar.gymtracker.base.application.BaseService;
import org.avillar.gymtracker.errors.application.BadFormException;
import org.avillar.gymtracker.errors.application.EntityNotFoundException;
import org.avillar.gymtracker.errors.application.IllegalAccessException;
import org.avillar.gymtracker.set.application.dto.SetDto;
import org.avillar.gymtracker.set.application.dto.SetDtoValidator;
import org.avillar.gymtracker.set.application.dto.SetMapper;
import org.avillar.gymtracker.set.domain.Set;
import org.avillar.gymtracker.set.domain.SetDao;
import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.avillar.gymtracker.setgroup.domain.SetGroupDao;
import org.avillar.gymtracker.sort.application.EntitySorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.DataBinder;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SetServiceImpl extends BaseService implements SetService {

    private final SetDao setDao;
    private final SetGroupDao setGroupDao;
    private final SetMapper setMapper;
    private final EntitySorter entitySorter;
    private final SetDtoValidator setDtoValidator;

    @Autowired
    public SetServiceImpl(SetDao setDao, SetGroupDao setGroupDao, SetMapper setMapper, EntitySorter entitySorter,
                          SetDtoValidator setDtoValidator) {
        this.setDao = setDao;
        this.setGroupDao = setGroupDao;
        this.setMapper = setMapper;
        this.entitySorter = entitySorter;
        this.setDtoValidator = setDtoValidator;
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public List<SetDto> getAllSetGroupSets(final Long setGroupId)
            throws EntityNotFoundException, IllegalAccessException {
        final SetGroup setGroup = this.setGroupDao.findById(setGroupId)
                .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId));
        this.authService.checkAccess(setGroup);
        return this.setMapper.toDtos(this.setDao.findBySetGroupOrderByListOrderAsc(setGroup), -1);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public SetDto getSet(final Long setId)
            throws EntityNotFoundException, IllegalAccessException {
        final Set set = this.setDao.findById(setId)
                .orElseThrow(() -> new EntityNotFoundException(Set.class, setId));
        this.authService.checkAccess(set.getSetGroup());
        return this.setMapper.toDto(set, -1);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public SetDto getSetDefaultDataForNewSet(Long setGroupId, Integer setNumber)
            throws EntityNotFoundException, IllegalAccessException {
        final SetGroup setGroup = this.setGroupDao.findById(setGroupId)
                .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId));
        this.authService.checkAccess(setGroup);

        final List<Set> sets = this.setDao.findLastSetForExerciseAndUser(this.authService.getLoggedUser(), setGroup.getExercise(), setNumber, setGroup.getWorkout().getDate());
        if (!sets.isEmpty()) {
            return this.setMapper.toDto(sets.get(0), -1);
        }//TODO Mejorar esto

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
    public SetDto createSet(final SetDto setDto)
            throws EntityNotFoundException, IllegalAccessException, BadFormException {
        final DataBinder dataBinder = new DataBinder(setDto);
        dataBinder.addValidators(this.setDtoValidator);
        dataBinder.validate();
        if (dataBinder.getBindingResult().hasErrors()) {
            throw new BadFormException(SetDto.class, dataBinder.getBindingResult());
        }

        final SetGroup setGroup = this.setGroupDao.getReferenceById(setDto.getSetGroup().getId());

        final Set set = this.setMapper.toEntity(setDto);

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
    public SetDto updateSet(final SetDto setDto)
            throws EntityNotFoundException, IllegalAccessException, BadFormException {
        final DataBinder dataBinder = new DataBinder(setDto);
        dataBinder.addValidators(this.setDtoValidator);
        dataBinder.validate();
        if (dataBinder.getBindingResult().hasErrors()) {
            throw new BadFormException(SetDto.class, dataBinder.getBindingResult());
        }

        final Set setDb = this.setDao.getReferenceById(setDto.getId());

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
    public void deleteSet(final Long setId)
            throws EntityNotFoundException, IllegalAccessException {
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
