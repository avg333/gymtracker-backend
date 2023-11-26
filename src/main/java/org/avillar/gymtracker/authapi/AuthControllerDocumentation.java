package org.avillar.gymtracker.authapi;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public interface AuthControllerDocumentation {

  @Tag(name = "Authentication", description = "API to manage Authentication")
  @Target(TYPE)
  @Retention(RUNTIME)
  @interface AuthControllerTag {}
}
