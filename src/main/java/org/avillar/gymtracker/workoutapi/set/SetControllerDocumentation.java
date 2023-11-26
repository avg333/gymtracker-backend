package org.avillar.gymtracker.workoutapi.set;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public interface SetControllerDocumentation {

  @Tag(name = "Sets", description = "API for managing sets")
  @Target(TYPE)
  @Retention(RUNTIME)
  @interface SetControllerTag {}
}
