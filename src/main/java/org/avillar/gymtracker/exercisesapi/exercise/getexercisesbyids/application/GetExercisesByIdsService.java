package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;

public interface GetExercisesByIdsService {

  List<Exercise> execute(Collection<UUID> exerciseIds) throws IllegalAccessException;
}
