package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.Exercise;
import org.avillar.gymtracker.model.LoadType;
import org.avillar.gymtracker.model.MuscleGroup;
import org.avillar.gymtracker.model.MuscleSubGroup;

import java.util.List;
import java.util.Optional;

public interface ExerciseService {
    List<MuscleGroup> getAllMuscleGroups();

    List <LoadType> getAllLoadTypes();

    List<MuscleSubGroup> getMuscleSubgroupsByMuscleGroup(MuscleGroup muscleGroup);

    List<Exercise> getAllExercises();

    Exercise getExerciseById(Long id);

    //TODO: ACABAR ESTA FUNCION
    List<Exercise> getExercisesByFilters(String exerciseName,Long idMuscleGroup, Long idSubMuscleGroup, Long idLoadType, Boolean unilateral);

    Exercise addExercise(Exercise exercise);

    Exercise updateExercise(Exercise exercise);

    boolean deleteExercise(Long id);
}
