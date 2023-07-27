package org.avillar.gymtracker.workoutapi.workout.createworkout.application.mapper;

import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.createworkout.application.model.CreateWorkoutRequestApplication;
import org.avillar.gymtracker.workoutapi.workout.createworkout.application.model.CreateWorkoutResponseApplication;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreateWorkoutServiceMapper {

  CreateWorkoutResponseApplication map(Workout workout);

  Workout map(CreateWorkoutRequestApplication createWorkoutRequestApplication);
}
