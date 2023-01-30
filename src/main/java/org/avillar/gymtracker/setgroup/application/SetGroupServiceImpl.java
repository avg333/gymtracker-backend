package org.avillar.gymtracker.setgroup.application;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.base.application.BaseService;
import org.avillar.gymtracker.exercise.domain.Exercise;
import org.avillar.gymtracker.exercise.domain.ExerciseDao;
import org.avillar.gymtracker.session.domain.Session;
import org.avillar.gymtracker.session.domain.SessionDao;
import org.avillar.gymtracker.set.domain.SetDao;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupDto;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupMapper;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupValidator;
import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.avillar.gymtracker.setgroup.domain.SetGroupDao;
import org.avillar.gymtracker.sort.application.EntitySorter;
import org.avillar.gymtracker.user.domain.UserApp;
import org.avillar.gymtracker.user.domain.UserDao;
import org.avillar.gymtracker.workout.domain.Workout;
import org.avillar.gymtracker.workout.domain.WorkoutDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class SetGroupServiceImpl extends BaseService implements SetGroupService {
    private static final String SESSION_NOT_FOUND_ERROR_MSG = "The session does not exist";
    private static final String WORKOUT_NOT_FOUND_ERROR_MSG = "The workout does not exist";
    private static final String NOT_FOUND_ERROR_MSG = "The setGroup does not exist";

    private final SetGroupDao setGroupDao;
    private final SessionDao sessionDao;
    private final WorkoutDao workoutDao;
    private final SetGroupMapper setGroupMapper;
    private final SetGroupValidator setGroupValidator;
    private final EntitySorter entitySorter;
    private final ExerciseDao exerciseDao;
    private final UserDao userDao;
    private final SetDao setDao;

    @Autowired
    public SetGroupServiceImpl(SetGroupDao setGroupDao, SessionDao sessionDao, WorkoutDao workoutDao,
                               SetGroupMapper setGroupMapper, SetGroupValidator setGroupValidator,
                               EntitySorter entitySorter, ExerciseDao exerciseDao, UserDao userDao, SetDao setDao) {
        this.setGroupDao = setGroupDao;
        this.sessionDao = sessionDao;
        this.workoutDao = workoutDao;
        this.setGroupMapper = setGroupMapper;
        this.setGroupValidator = setGroupValidator;
        this.entitySorter = entitySorter;
        this.exerciseDao = exerciseDao;
        this.userDao = userDao;
        this.setDao = setDao;
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public List<SetGroupDto> getAllSessionSetGroups(final Long sessionId) throws EntityNotFoundException, IllegalAccessException {
        final Session session = this.sessionDao.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException(SESSION_NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(session.getProgram());
        return this.setGroupMapper.toDtos(this.setGroupDao.findBySessionOrderByListOrderAsc(session), -1);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public List<SetGroupDto> getAllWorkoutSetGroups(final Long workoutId) throws EntityNotFoundException, IllegalAccessException {
        final Workout workout = this.workoutDao.findById(workoutId)
                .orElseThrow(() -> new EntityNotFoundException(WORKOUT_NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(workout);
        return this.setGroupMapper.toDtos(this.setGroupDao.findByWorkoutOrderByListOrderAsc(workout), -1);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public SetGroupDto getLastTimeUserExerciseSetGroup(final Long userId, final Long exerciseId) throws IllegalAccessException {
        final Exercise exercise = this.exerciseDao.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        final UserApp userApp = this.userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));

        final List<SetGroup> setGroups = this.setGroupDao.findLastUserExerciseSetGroup(userApp, exercise);
        if (CollectionUtils.isEmpty(setGroups)) {
            throw new EntityNotFoundException(NOT_FOUND_ERROR_MSG);
        }
        this.authService.checkAccess(setGroups.get(0));
        return this.setGroupMapper.toDto(setGroups.get(0), -1);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public SetGroupDto getSetGroup(Long setGroupId) throws EntityNotFoundException, IllegalAccessException {
        final SetGroup setGroup = this.setGroupDao.findById(setGroupId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(setGroup);
        return this.setGroupMapper.toDto(setGroup, -1);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public SetGroupDto createSetGroupInWorkout(final SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException {
        if (!this.setGroupValidator.validate(setGroupDto, new HashMap<>()).isEmpty()) {
            throw new RuntimeException("El set esta mal formado");
        }// TODO Mejorar devolucion de errores


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
    public SetGroupDto createSetGroupInSession(final SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException {
        if (!this.setGroupValidator.validate(setGroupDto, new HashMap<>()).isEmpty()) {
            throw new RuntimeException("El set esta mal formado");
        }// TODO Mejorar devolucion de errores

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

    @Override
    @Transactional
    public SetGroupDto replaceSetGroupSetsFromSetGroup(final Long setGroupDestinationId, final Long setGroupSourceId) throws IllegalAccessException {
        final SetGroup setGroupDestination = this.setGroupDao.findById(setGroupDestinationId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        final SetGroup setGroupSource = this.setGroupDao.findById(setGroupSourceId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));

        this.authService.checkAccess(setGroupDestination);
        this.authService.checkAccess(setGroupSource);

        //TODO Mejorar
        final var sets = new ArrayList<org.avillar.gymtracker.set.domain.Set>();
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
    public SetGroupDto updateSetGroup(SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException {
        if (!this.setGroupValidator.validate(setGroupDto, new HashMap<>()).isEmpty()) {
            throw new RuntimeException("El set esta mal formado");
        }// TODO Mejorar devolucion de errores


        final SetGroup setGroupDb = this.setGroupDao.findById(setGroupDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(setGroupDb);

        final int oldPosition = setGroupDb.getListOrder();

        final SetGroup setGroup = this.setGroupMapper.toEntity(setGroupDto);

        if (setGroupDb.getSession() != null) {
            setGroup.setSession(setGroupDb.getSession());
        } else {
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
    public void deleteSetGroup(final Long setGroupId) throws EntityNotFoundException, IllegalAccessException {
        final SetGroup setGroup = this.setGroupDao.findById(setGroupId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
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
