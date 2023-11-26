package org.avillar.gymtracker.usersapi;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public interface SettingsControllerDocumentation {

  @Tag(name = "Settings", description = "API to manage Settings")
  @Target(TYPE)
  @Retention(RUNTIME)
  @interface SettingsControllerTag {}
}
