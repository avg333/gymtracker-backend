package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.ExerciseDao;
import org.avillar.gymtracker.model.dto.ExerciseDto;
import org.avillar.gymtracker.model.entities.Exercise;
import org.avillar.gymtracker.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ExerciseServiceImpl extends BaseService implements ExerciseService {

    private static final String NOT_FOUND_ERROR_MSG = "El programa no existe";

    private final ExerciseDao exerciseDao;

    @Autowired
    public ExerciseServiceImpl(ExerciseDao exerciseDao) {
        this.exerciseDao = exerciseDao;
    }

    @Override
    public List<ExerciseDto> getAllExercises() {
        return this.exerciseDao.findAll().stream().map(exercise -> this.modelMapper.map(exercise, ExerciseDto.class)).toList();
    }

    @Override
    public ExerciseDto getExercise(final Long exerciseId) {
        final Exercise exercise = this.exerciseDao.findById(exerciseId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        return this.modelMapper.map(exercise, ExerciseDto.class);
    }

    @Override
    public ExerciseDto createExercise(final ExerciseDto exerciseDto) {
        final Exercise exercise = this.modelMapper.map(exerciseDto, Exercise.class);
        return this.modelMapper.map(this.exerciseDao.save(exercise), ExerciseDto.class);
    }

    @Override
    public ExerciseDto updateExercise(final ExerciseDto exerciseDto) {
        final Exercise exercise = this.modelMapper.map(exerciseDto, Exercise.class);
        return this.modelMapper.map(this.exerciseDao.save(exercise), ExerciseDto.class);
    }

    @Override
    public void deleteExercise(final Long exerciseId) {
        this.exerciseDao.deleteById(exerciseId);
    }
}