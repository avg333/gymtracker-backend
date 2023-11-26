package org.avillar.gymtracker.workoutapi.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "workout")
public class WorkoutProperties {

  private int defaultRest;
}
