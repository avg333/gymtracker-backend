package org.avillar.gymtracker.exercisesapi.loadtype;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public interface LoadTypesControllerDocumentation {

  @Tag(name = "LoadTypes", description = "API to manage LoadTypes")
  @Target(TYPE)
  @Retention(RUNTIME)
  @interface LoadTypesControllerTag {}
}
