package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.ExerciseDao;
import org.avillar.gymtracker.model.entities.Exercise;
import org.avillar.gymtracker.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseDao exerciseDao;

    @Autowired
    public ExerciseServiceImpl(ExerciseDao exerciseDao) {
        this.exerciseDao = exerciseDao;
    }

    @Override
    public List<Exercise> getAllExercises() {
        return this.exerciseDao.findAll();
    }

    @Override
    public Exercise getExercise(final Long exerciseId) {
        return this.exerciseDao.findById(exerciseId).orElse(null);
    }

    @Override
    public Exercise createExercise(final Exercise exercise) {
        return this.exerciseDao.save(exercise);
    }

    @Override
    public Exercise updateExercise(final Long exerciseId, final Exercise exercise) {
        exercise.setId(exerciseId);
        return this.exerciseDao.save(exercise);
    }

    @Override
    public void deleteExercise(final Long exerciseId) {
        this.exerciseDao.deleteById(exerciseId);
    }


}
