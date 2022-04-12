package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.ExerciseRepository;
import org.avillar.gymtracker.model.entities.Exercise;
import org.avillar.gymtracker.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public List<Exercise> getAllExercises() {
        return this.exerciseRepository.findAll();
    }

    @Override
    public Exercise getExercise(final Long exerciseId) {
        return this.exerciseRepository.findById(exerciseId).orElse(null);
    }

    @Override
    public Exercise createExercise(final Exercise exercise) {
        return this.exerciseRepository.save(exercise);
    }

    @Override
    public Exercise updateExercise(final Long exerciseId, final Exercise exercise) {
        exercise.setId(exerciseId);
        return this.exerciseRepository.save(exercise);
    }

    @Override
    public void deleteExercise(final Long exerciseId) {
        this.exerciseRepository.deleteById(exerciseId);
    }


}
