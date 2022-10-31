package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.WorkoutDao;
import org.avillar.gymtracker.model.dto.WorkoutDto;
import org.avillar.gymtracker.model.entities.Workout;
import org.avillar.gymtracker.services.LoginService;
import org.avillar.gymtracker.services.WorkoutService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

@Service
public class WorkoutServiceImpl implements WorkoutService {
    private static final String NOT_FOUND_ERROR_MSG = "El programa no existe";

    private final WorkoutDao workoutDao;
    private final ModelMapper modelMapper;
    private final LoginService loginService;

    public WorkoutServiceImpl(WorkoutDao workoutDao, ModelMapper modelMapper, LoginService loginService) {
        this.workoutDao = workoutDao;
        this.modelMapper = modelMapper;
        this.loginService = loginService;
    }

    @Override
    public List<WorkoutDto> getAllWorkouts() {
        final List<Workout> workouts = this.workoutDao.findByUserAppOrderByDateDesc(this.loginService.getLoggedUser());
        return workouts.stream().map(workout -> this.modelMapper.map(workout, WorkoutDto.class)).toList();

    }

    @Override
    public List<WorkoutDto> getWorkoutByDate(final Date date) throws EntityNotFoundException {
        return null;
    }

    @Override
    public WorkoutDto getWorkout(final Long workoutId) throws EntityNotFoundException, IllegalAccessException {
        final Workout workout = this.workoutDao.findById(workoutId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(workout);
        return this.modelMapper.map(workout, WorkoutDto.class);
    }

    @Override
    public WorkoutDto createWorkout(final WorkoutDto workoutDto) throws EntityNotFoundException {
        workoutDto.setId(null);
        final Workout workout = this.modelMapper.map(workoutDto, Workout.class);
        return this.modelMapper.map(this.workoutDao.save(workout), WorkoutDto.class);
    }

    @Override
    public WorkoutDto updateWorkout(final WorkoutDto workoutDto) throws EntityNotFoundException, IllegalAccessException {
        final Workout workoutDb = this.workoutDao.findById(workoutDto.getId()).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(workoutDb);
        final Workout workout = this.modelMapper.map(workoutDto, Workout.class);
        return this.modelMapper.map(this.workoutDao.save(workout), WorkoutDto.class);
    }

    @Override
    public void deleteWorkout(Long workoutId) throws EntityNotFoundException, IllegalAccessException {
        final Workout workout = this.workoutDao.findById(workoutId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(workout);
        this.workoutDao.deleteById(workoutId);
    }
}
