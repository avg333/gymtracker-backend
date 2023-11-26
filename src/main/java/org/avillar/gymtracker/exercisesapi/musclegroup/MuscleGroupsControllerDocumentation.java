package org.avillar.gymtracker.exercisesapi.musclegroup;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public interface MuscleGroupsControllerDocumentation {

  @Tag(name = "MuscleGroups", description = "API to manage MuscleGroups")
  @Target(TYPE)
  @Retention(RUNTIME)
  @interface MuscleGroupsControllerTag {}
}
