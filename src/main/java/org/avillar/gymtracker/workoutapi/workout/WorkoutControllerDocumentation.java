package org.avillar.gymtracker.workoutapi.workout;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public interface WorkoutControllerDocumentation {

  @Tag(name = "Workouts", description = "API to manage Workouts")
  @Target(TYPE)
  @Retention(RUNTIME)
  @interface WorkoutControllerTag {}
}
