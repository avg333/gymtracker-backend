package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.dao.ExerciseRepository;
import org.avillar.gymtracker.dao.LoadTypeRepository;
import org.avillar.gymtracker.dao.MuscleGroupRepository;
import org.avillar.gymtracker.dao.MuscleSubGroupRepository;
import org.avillar.gymtracker.model.Exercise;
import org.avillar.gymtracker.model.LoadType;
import org.avillar.gymtracker.model.MuscleGroup;
import org.avillar.gymtracker.model.MuscleSubGroup;
import org.avillar.gymtracker.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final LoadTypeRepository loadTypeRepository;
    private final MuscleSubGroupRepository muscleSubGroupRepository;
    private final MuscleGroupRepository muscleGroupRepository;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, LoadTypeRepository loadTypeRepository,
                               MuscleSubGroupRepository muscleSubGroupRepository,
                               MuscleGroupRepository muscleGroupRepository) {
        this.exerciseRepository = exerciseRepository;
        this.loadTypeRepository = loadTypeRepository;
        this.muscleSubGroupRepository = muscleSubGroupRepository;
        this.muscleGroupRepository = muscleGroupRepository;
    }

    @Override
    public List<MuscleGroup> getAllMuscleGroups() {
        return this.muscleGroupRepository.findAll();
    }

    @Override
    public List<LoadType> getAllLoadTypes() {
        return this.loadTypeRepository.findAll();
    }

    @Override
    public List<MuscleSubGroup> getMuscleSubgroupsByMuscleGroup(final MuscleGroup muscleGroup) {
        return this.muscleSubGroupRepository.findByMuscleGroup(muscleGroup);
    }

    @Override
    public List<Exercise> getAllExercises() {
        return this.exerciseRepository.findAll();
    }

    @Override
    public Exercise getExerciseById(final Long id) {
        return this.exerciseRepository.findById(id).orElse(null);
    }

    //TODO: ACABAR ESTA FUNCION
    @Override
    public List<Exercise> getExercisesByFilters(String exerciseName, Long idMuscleGroup, Long idSubMuscleGroup, Long idLoadType, Boolean unilateral){
        return this.exerciseRepository.findByFilters(idMuscleGroup, idLoadType, unilateral);
    }

    @Override
    public Exercise addExercise(final Exercise exercise) {
        return this.exerciseRepository.save(exercise);
    }

    @Override
    public Exercise updateExercise(final Exercise exercise) {
        if (this.exerciseRepository.existsById(exercise.getId())){
            return null;
        }
        return this.exerciseRepository.save(exercise);
    }

    @Override
    public boolean deleteExercise(final Long id) {
        if (this.exerciseRepository.existsById(id)) {
            this.exerciseRepository.deleteById(id);
            return true;
        }

        return false;
    }


}
