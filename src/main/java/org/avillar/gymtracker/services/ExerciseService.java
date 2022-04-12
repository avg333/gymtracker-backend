package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.entities.Exercise;

import java.util.List;

public interface ExerciseService {

    List<Exercise> getAllExercises();

    Exercise getExercise(Long exerciseId);

    Exercise createExercise(Exercise exercise);

    Exercise updateExercise(Long exerciseId, Exercise exercise);

    void deleteExercise(Long exerciseId);
}
