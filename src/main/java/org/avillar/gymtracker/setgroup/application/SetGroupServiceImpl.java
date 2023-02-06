package org.avillar.gymtracker.setgroup.application;

import org.avillar.gymtracker.base.application.BaseService;
import org.avillar.gymtracker.errors.application.BadFormException;
import org.avillar.gymtracker.errors.application.EntityNotFoundException;
import org.avillar.gymtracker.errors.application.IllegalAccessException;
import org.avillar.gymtracker.session.domain.Session;
import org.avillar.gymtracker.session.domain.SessionDao;
import org.avillar.gymtracker.set.domain.SetDao;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupDto;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupDtoValidator;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupMapper;
import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.avillar.gymtracker.setgroup.domain.SetGroupDao;
import org.avillar.gymtracker.sort.application.EntitySorter;
import org.avillar.gymtracker.workout.domain.Workout;
import org.avillar.gymtracker.workout.domain.WorkoutDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.DataBinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class SetGroupServiceImpl extends BaseService implements SetGroupService {

    private final SetGroupDao setGroupDao;
    private final SessionDao sessionDao;
    private final WorkoutDao workoutDao;
    private final SetDao setDao;
    private final SetGroupMapper setGroupMapper;
    private final EntitySorter entitySorter;

    @Autowired
    public SetGroupServiceImpl(SetGroupDao setGroupDao, SessionDao sessionDao, WorkoutDao workoutDao, SetDao setDao,
                               SetGroupMapper setGroupMapper, EntitySorter entitySorter) {
        this.setGroupDao = setGroupDao;
        this.sessionDao = sessionDao;
        this.workoutDao = workoutDao;
        this.setDao = setDao;
        this.setGroupMapper = setGroupMapper;
        this.entitySorter = entitySorter;
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public List<SetGroupDto> getAllSessionSetGroups(final Long sessionId)
            throws EntityNotFoundException, IllegalAccessException {
        final Session session = this.sessionDao.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException(Session.class, sessionId));
        this.authService.checkAccess(session.getProgram());
        return this.setGroupMapper.toDtos(this.setGroupDao.findBySessionOrderByListOrderAsc(session), -1);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public List<SetGroupDto> getAllWorkoutSetGroups(final Long workoutId)
            throws EntityNotFoundException, IllegalAccessException {
        final Workout workout = this.workoutDao.findById(workoutId)
                .orElseThrow(() -> new EntityNotFoundException(Workout.class, workoutId));
        this.authService.checkAccess(workout);
        return this.setGroupMapper.toDtos(this.setGroupDao.findByWorkoutOrderByListOrderAsc(workout), -1);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public SetGroupDto getSetGroup(final Long setGroupId)
            throws EntityNotFoundException, IllegalAccessException {
        final SetGroup setGroup = this.setGroupDao.findById(setGroupId)
                .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId));
        this.authService.checkAccess(setGroup);
        return this.setGroupMapper.toDto(setGroup, -1);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public SetGroupDto createSetGroupInWorkout(final SetGroupDto setGroupDto)
            throws EntityNotFoundException, IllegalAccessException, BadFormException {
        final DataBinder dataBinder = new DataBinder(setGroupDto);
        dataBinder.addValidators(new SetGroupDtoValidator());
        dataBinder.validate();
        if (dataBinder.getBindingResult().hasErrors()) {
            throw new BadFormException(SetGroupDto.class, dataBinder.getBindingResult());
        }

        final Workout workout = this.workoutDao.getReferenceById(setGroupDto.getWorkout().getId());
        this.authService.checkAccess(workout);

        final SetGroup setGroup = this.setGroupMapper.toEntity(setGroupDto);
        final int setGroupsSize = this.setGroupDao.findByWorkoutOrderByListOrderAsc(workout).size();
        if (null == setGroup.getListOrder() || setGroup.getListOrder() > setGroupsSize || 0 > setGroup.getListOrder()) {
            setGroup.setListOrder(setGroupsSize);
        }

        this.setGroupDao.save(setGroup);

        final Set<SetGroup> setGroups = setGroup.getWorkout().getSetGroups();
        if (this.entitySorter.sortPost(setGroups, setGroup)) {
            this.setGroupDao.saveAll(setGroups);
        }

        return this.setGroupMapper.toDto(setGroup, -1);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public SetGroupDto createSetGroupInSession(final SetGroupDto setGroupDto)
            throws EntityNotFoundException, IllegalAccessException, BadFormException {
        final DataBinder dataBinder = new DataBinder(setGroupDto);
        dataBinder.addValidators(new SetGroupDtoValidator());
        dataBinder.validate();
        if (dataBinder.getBindingResult().hasErrors()) {
            throw new BadFormException(SetGroupDto.class, dataBinder.getBindingResult());
        }

        final Session session = this.sessionDao.getReferenceById(setGroupDto.getSession().getId());
        this.authService.checkAccess(session.getProgram());

        final SetGroup setGroup = this.setGroupMapper.toEntity(setGroupDto);
        final int setGroupsSize = this.setGroupDao.findBySessionOrderByListOrderAsc(session).size();
        if (null == setGroup.getListOrder() || setGroup.getListOrder() > setGroupsSize || 0 > setGroup.getListOrder()) {
            setGroup.setListOrder(setGroupsSize);
        }

        this.setGroupDao.save(setGroup);

        final Set<SetGroup> setGroups = setGroup.getSession().getSetGroups();
        if (this.entitySorter.sortPost(setGroups, setGroup)) {
            this.setGroupDao.saveAll(setGroups);
        }

        return this.setGroupMapper.toDto(setGroup, -1);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public SetGroupDto replaceSetGroupSetsFromSetGroup(final Long setGroupDestinationId, final Long setGroupSourceId)
            throws EntityNotFoundException, IllegalAccessException {
        final SetGroup setGroupDestination = this.setGroupDao.findById(setGroupDestinationId)
                .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupDestinationId));
        final SetGroup setGroupSource = this.setGroupDao.findById(setGroupSourceId)
                .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupSourceId));

        this.authService.checkAccess(setGroupDestination);
        this.authService.checkAccess(setGroupSource);

        //TODO Mejorar
        final var sets = new ArrayList<org.avillar.gymtracker.set.domain.Set>(setGroupSource.getSets().size());
        for (final var setDb : setGroupSource.getSets()) {
            final org.avillar.gymtracker.set.domain.Set set = new org.avillar.gymtracker.set.domain.Set();
            set.setSetGroup(setGroupDestination);
            set.setListOrder(setDb.getListOrder());
            set.setDescription(setDb.getDescription());
            set.setReps(setDb.getReps());
            set.setRir(setDb.getRir());
            set.setWeight(setDb.getWeight());
            sets.add(set);
        }

        this.setDao.deleteAllById(setGroupDestination.getSets().stream().map(org.avillar.gymtracker.set.domain.Set::getId).toList());
        this.setDao.saveAll(sets);

        return this.setGroupMapper.toDto(this.setGroupDao.getReferenceById(setGroupDestinationId), -1);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public SetGroupDto updateSetGroup(final SetGroupDto setGroupDto)
            throws EntityNotFoundException, IllegalAccessException, BadFormException {
        final DataBinder dataBinder = new DataBinder(setGroupDto);
        dataBinder.addValidators(new SetGroupDtoValidator());
        dataBinder.validate();
        if (dataBinder.getBindingResult().hasErrors()) {
            throw new BadFormException(SetGroupDto.class, dataBinder.getBindingResult());
        }

        final SetGroup setGroupDb = this.setGroupDao.getReferenceById(setGroupDto.getId());
        this.authService.checkAccess(setGroupDb);

        final int oldPosition = setGroupDb.getListOrder();

        final SetGroup setGroup = this.setGroupMapper.toEntity(setGroupDto);

        if (setGroupDb.getSession() != null) {
            setGroup.setSession(setGroupDb.getSession());
        } else if (setGroupDb.getWorkout() != null) {
            setGroup.setWorkout(setGroupDb.getWorkout());
        }

        this.setGroupDao.save(this.setGroupMapper.toEntity(setGroupDto));

        final Set<SetGroup> setGroups = setGroup.getSession() != null
                ? setGroup.getSession().getSetGroups()
                : setGroup.getWorkout().getSetGroups();
        if (this.entitySorter.sortUpdate(setGroups, setGroup, oldPosition)) {
            this.setGroupDao.saveAll(setGroups);
        }

        return this.setGroupMapper.toDto(setGroup, -1);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteSetGroup(final Long setGroupId)
            throws EntityNotFoundException, IllegalAccessException {
        final SetGroup setGroup = this.setGroupDao.findById(setGroupId)
                .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId));
        this.authService.checkAccess(setGroup);

        this.setGroupDao.deleteById(setGroupId);

        final Set<SetGroup> setGroups = setGroup.getSession() != null
                ? setGroup.getSession().getSetGroups()
                : setGroup.getWorkout().getSetGroups();
        if (this.entitySorter.sortDelete(setGroups, setGroup)) {
            this.setGroupDao.saveAll(setGroups);
        }
    }

}
