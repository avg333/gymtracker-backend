package org.avillar.gymtracker.exercisesapi.exercise;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public interface ExerciseControllerDocumentation {

  @Tag(name = "Exercises", description = "API to manage Exercises")
  @Target(TYPE)
  @Retention(RUNTIME)
  @interface ExerciseControllerTag {}
}
