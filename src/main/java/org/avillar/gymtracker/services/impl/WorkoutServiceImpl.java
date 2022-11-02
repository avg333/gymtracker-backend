package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.UserDao;
import org.avillar.gymtracker.model.dao.WorkoutDao;
import org.avillar.gymtracker.model.dto.WorkoutDto;
import org.avillar.gymtracker.model.entities.UserApp;
import org.avillar.gymtracker.model.entities.Workout;
import org.avillar.gymtracker.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class WorkoutServiceImpl extends BaseService implements WorkoutService {
    private static final String NOT_FOUND_ERROR_MSG = "El programa no existe";

    private final WorkoutDao workoutDao;
    private final UserDao userDao;

    @Autowired
    public WorkoutServiceImpl(WorkoutDao workoutDao, UserDao userDao) {
        this.workoutDao = workoutDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutDto> getAllUserWorkouts(final Long userId) throws IllegalAccessException {
        final UserApp userApp = this.userDao.findById(userId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        final List<Workout> workouts = this.workoutDao.findByUserAppOrderByDateDesc(userApp);
        final List<WorkoutDto> workoutDtos = new ArrayList<>(workouts.size());

        for (final Workout workout : workouts) {
            this.loginService.checkAccess(workout);
            workoutDtos.add(this.modelMapper.map(workout, WorkoutDto.class));
        }

        return workoutDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutDto> getWorkoutByDate(final Date date) throws EntityNotFoundException {
        return Collections.emptyList(); //TODO Por realizar
    }

    @Override
    @Transactional(readOnly = true)
    public WorkoutDto getWorkout(final Long workoutId) throws EntityNotFoundException, IllegalAccessException {
        final Workout workout = this.workoutDao.findById(workoutId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(workout);
        return this.modelMapper.map(workout, WorkoutDto.class);
    }

    @Override
    @Transactional
    public WorkoutDto createWorkout(final WorkoutDto workoutDto) throws EntityNotFoundException {
        final Workout workout = this.modelMapper.map(workoutDto, Workout.class);
        workout.setUserApp(this.loginService.getLoggedUser());
        return this.modelMapper.map(this.workoutDao.save(workout), WorkoutDto.class);
    }

    @Override
    @Transactional
    public WorkoutDto updateWorkout(final WorkoutDto workoutDto) throws EntityNotFoundException, IllegalAccessException {
        final Workout workoutDb = this.workoutDao.findById(workoutDto.getId()).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(workoutDb);
        final Workout workout = this.modelMapper.map(workoutDto, Workout.class);
        workout.setUserApp(workoutDb.getUserApp());
        return this.modelMapper.map(this.workoutDao.save(workout), WorkoutDto.class);
    }

    @Override
    @Transactional
    public void deleteWorkout(Long workoutId) throws EntityNotFoundException, IllegalAccessException {
        final Workout workout = this.workoutDao.findById(workoutId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(workout);
        this.workoutDao.deleteById(workoutId);
    }
}
