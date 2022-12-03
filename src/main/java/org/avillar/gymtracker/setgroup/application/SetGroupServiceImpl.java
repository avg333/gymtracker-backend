package org.avillar.gymtracker.setgroup.application;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.base.application.BaseService;
import org.avillar.gymtracker.exercise.application.dto.ExerciseMapper;
import org.avillar.gymtracker.exercise.domain.Exercise;
import org.avillar.gymtracker.exercise.domain.ExerciseDao;
import org.avillar.gymtracker.session.domain.Session;
import org.avillar.gymtracker.session.domain.SessionDao;
import org.avillar.gymtracker.set.application.SetDto;
import org.avillar.gymtracker.set.domain.Set;
import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.avillar.gymtracker.setgroup.domain.SetGroupDao;
import org.avillar.gymtracker.workout.domain.Workout;
import org.avillar.gymtracker.workout.domain.WorkoutDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class SetGroupServiceImpl extends BaseService implements SetGroupService {
    private static final String NOT_FOUND_ERROR_MSG = "El SetGroup no existe";

    private final SetGroupDao setGroupDao;
    private final SessionDao sessionDao;
    private final WorkoutDao workoutDao;
    private final ExerciseDao exerciseDao;
    private final ExerciseMapper exerciseMapper;

    @Autowired
    public SetGroupServiceImpl(SetGroupDao setGroupDao, SessionDao sessionDao, WorkoutDao workoutDao,
                               ExerciseDao exerciseDao, ExerciseMapper exerciseMapper) {
        this.setGroupDao = setGroupDao;
        this.sessionDao = sessionDao;
        this.workoutDao = workoutDao;
        this.exerciseDao = exerciseDao;
        this.exerciseMapper = exerciseMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SetGroupDto> getAllSessionSetGroups(final Long sessionId) throws IllegalAccessException {
        final Session session = this.sessionDao.findById(sessionId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(session.getProgram());
        final List<SetGroup> setGroups = this.setGroupDao.findBySessionOrderByListOrderAsc(session);
        return setGroups.stream().map(this::makeSetGroupDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SetGroupDto> getAllWorkoutSetGroups(final Long workoutId) throws IllegalAccessException {
        final Workout workout = this.workoutDao.findById(workoutId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(workout);
        final List<SetGroup> setGroups = this.setGroupDao.findByWorkoutOrderByListOrderAsc(workout);
        return setGroups.stream().map(this::makeSetGroupDto).toList();
    }

    private SetGroupDto makeSetGroupDto(final SetGroup setGroup) {
        final SetGroupDto setGroupDto = this.modelMapper.map(setGroup, SetGroupDto.class);
        final Exercise exercise = this.exerciseDao.findById(setGroupDto.getExerciseId()).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        setGroupDto.setExerciseDto(this.exerciseMapper.toDto(exercise, true));
        setGroupDto.setSetDtoList(setGroup.getSets().stream().sorted(Comparator.comparing(Set::getListOrder)).map(set -> this.modelMapper.map(set, SetDto.class)).toList());
        return setGroupDto;
    }

    @Override
    @Transactional(readOnly = true)
    public SetGroupDto getSetGroup(Long setGroupId) throws EntityNotFoundException, IllegalAccessException {
        final SetGroup setGroup = this.setGroupDao.findById(setGroupId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(setGroup);
        return this.makeSetGroupDto(setGroup);
    }

    @Override
    @Transactional
    public SetGroupDto createSetGroupInWorkout(final SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException {
        final Workout workout = this.workoutDao.findById(setGroupDto.getWorkoutId()).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(workout);

        final SetGroup setGroup = this.modelMapper.map(setGroupDto, SetGroup.class);
        final int setGroupsSize = this.setGroupDao.findByWorkoutOrderByListOrderAsc(workout).size();
        if (null == setGroup.getListOrder() || setGroup.getListOrder() > setGroupsSize || 0 > setGroup.getListOrder()) {
            setGroup.setListOrder(setGroupsSize);
            this.setGroupDao.save(setGroup);
        } else {
            this.setGroupDao.save(setGroup);
            final List<SetGroup> setGroups = this.setGroupDao.findBySessionOrderByListOrderAsc(setGroup.getSession());
            this.reorderAllSessionSetGroupsAfterPost(setGroups, setGroup);
        }

        return this.modelMapper.map(setGroup, SetGroupDto.class);
    }

    @Override
    @Transactional
    public SetGroupDto createSetGroupInSession(final SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException {
        final Session session = this.sessionDao.findById(setGroupDto.getSessionId()).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(session.getProgram());

        final SetGroup setGroup = this.modelMapper.map(setGroupDto, SetGroup.class);
        final int setGroupsSize = this.setGroupDao.findBySessionOrderByListOrderAsc(session).size();
        if (null == setGroup.getListOrder() || setGroup.getListOrder() > setGroupsSize || 0 > setGroup.getListOrder()) {
            setGroup.setListOrder(setGroupsSize);
            this.setGroupDao.save(setGroup);
        } else {
            this.setGroupDao.save(setGroup);
            final List<SetGroup> setGroups = this.setGroupDao.findByWorkoutOrderByListOrderAsc(setGroup.getWorkout());
            this.reorderAllSessionSetGroupsAfterPost(setGroups, setGroup);
        }

        return this.modelMapper.map(setGroup, SetGroupDto.class);
    }

    @Override
    @Transactional
    public SetGroupDto updateSetGroup(SetGroupDto setGroupDto) throws EntityNotFoundException, IllegalAccessException {
        if (null == setGroupDto.getId()) {
            throw new EntityNotFoundException(NOT_FOUND_ERROR_MSG);
        }
        final SetGroup setGroupDb = this.setGroupDao.findById(setGroupDto.getId()).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(setGroupDb);
        SetGroup setGroup = this.modelMapper.map(setGroupDto, SetGroup.class);

        final int oldPosition = setGroupDb.getListOrder();
        this.setGroupDao.save(setGroup);
        final List<SetGroup> setGroups = setGroup.getSession() != null
                ? this.setGroupDao.findBySessionOrderByListOrderAsc(setGroup.getSession())
                : this.setGroupDao.findByWorkoutOrderByListOrderAsc(setGroup.getWorkout());
        this.reorderAllSessionSetGroupsAfterUpdate(setGroups, setGroup, oldPosition);

        return this.modelMapper.map(setGroup, SetGroupDto.class);
    }

    @Override
    @Transactional
    public void deleteSetGroup(Long setGroupId) throws EntityNotFoundException, IllegalAccessException {
        final SetGroup setGroup = this.setGroupDao.findById(setGroupId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(setGroup);

        final int setGroupPosition = setGroup.getListOrder();
        this.setGroupDao.deleteById(setGroupId);

        final List<SetGroup> setGroups = setGroup.getSession() != null
                ? this.setGroupDao.findBySessionOrderByListOrderAsc(setGroup.getSession())
                : this.setGroupDao.findByWorkoutOrderByListOrderAsc(setGroup.getWorkout());
        this.reorderAllSessionSetGroupsAfterDelete(setGroups, setGroupPosition);
    }

    private void reorderAllSessionSetGroupsAfterDelete(final List<SetGroup> setGroups, final int sessionPosition) {
        if (setGroups.isEmpty()) {
            return;
        }

        for (final SetGroup setGroup : setGroups) {
            if (setGroup.getListOrder() > sessionPosition) {
                setGroup.setListOrder(setGroup.getListOrder() - 1);
            }
        }

        this.setGroupDao.saveAll(setGroups);
    }

    private void reorderAllSessionSetGroupsAfterUpdate(final List<SetGroup> setGroups, final SetGroup newSetGroup, int oldPosition) {
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

        this.setGroupDao.saveAll(setGroups);
    }

    private void reorderAllSessionSetGroupsAfterPost(final List<SetGroup> setGroups, final SetGroup newSetGroup) {
        if (setGroups.isEmpty()) {
            return;
        }

        for (final SetGroup setGroup : setGroups) {
            if (!newSetGroup.getId().equals(setGroup.getId()) &&
                    newSetGroup.getListOrder() <= setGroup.getListOrder()) {
                setGroup.setListOrder(setGroup.getListOrder() + 1);
            }
        }

        this.setGroupDao.saveAll(setGroups);
    }
}