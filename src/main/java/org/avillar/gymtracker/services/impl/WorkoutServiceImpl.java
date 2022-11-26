package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.UserDao;
import org.avillar.gymtracker.model.dao.WorkoutDao;
import org.avillar.gymtracker.model.dto.WorkoutDto;
import org.avillar.gymtracker.model.dto.WorkoutSummaryDto;
import org.avillar.gymtracker.model.entities.*;
import org.avillar.gymtracker.model.entities.Set;
import org.avillar.gymtracker.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
    public List<Date> getAllUserWorkoutsDates(final Long userId) throws IllegalAccessException {
        final UserApp userApp = this.userDao.findById(userId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        final Workout workout = new Workout();
        workout.setUserApp(userApp);
        this.loginService.checkAccess(workout);

        return this.workoutDao.findByUserAppOrderByDateDesc(userApp).stream().map(Workout::getDate).toList();
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
    @Transactional(readOnly = true)
    public WorkoutSummaryDto getWorkoutSummary(final Long workoutId) throws EntityNotFoundException, IllegalAccessException {
        final Workout workout = this.workoutDao.findById(workoutId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(workout);
        final WorkoutSummaryDto workoutSummaryDto = new WorkoutSummaryDto();

        final java.util.Set<Long> exercisesId = new HashSet<>();
        final java.util.Set<String> muscles = new HashSet<>();
        int sets = 0;
        double weight = 0;

        Date startWo = null;
        Date endWo = null;
        for(final SetGroup setGroup: workout.getSetGroups()){
            for(final Set set: setGroup.getSets()){
                if(startWo == null || set.getLastModifiedAt().getTime() < startWo.getTime()){
                    startWo = set.getLastModifiedAt();
                }
                if(endWo == null || set.getLastModifiedAt().getTime() > endWo.getTime()){
                    endWo = set.getLastModifiedAt();
                }
                if(set.getRir() < 3){
                    exercisesId.add(setGroup.getExercise().getId());
                    final List<MuscleGroup> muscleGroups = new ArrayList<>(setGroup.getExercise().getMuscleGroups());
                    if(!muscleGroups.isEmpty()){
                        muscles.add(muscleGroups.get(0).getName());
                    }
                    sets++;
                    weight+= set.getWeight();
                }
            }
        }

        if(startWo != null && endWo != null){
            final long duration = endWo.getTime() - startWo.getTime();
            workoutSummaryDto.setDuration((int) TimeUnit.MILLISECONDS.toMinutes(duration));
        }
        workoutSummaryDto.setExerciseNumber(exercisesId.size());
        workoutSummaryDto.setMuscles(new ArrayList<>(muscles));
        workoutSummaryDto.setSetsNumber(sets);
        workoutSummaryDto.setWeightVolume((int) weight);

        return workoutSummaryDto;
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
