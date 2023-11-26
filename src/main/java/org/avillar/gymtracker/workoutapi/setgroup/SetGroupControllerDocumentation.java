package org.avillar.gymtracker.workoutapi.setgroup;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public interface SetGroupControllerDocumentation {

  @Tag(name = "SetGroups", description = "API for managing exercise SetGroups")
  @Target(TYPE)
  @Retention(RUNTIME)
  @interface SetGroupControllerTag {}
}
