package org.avillar.gymtracker.usersapi.common.properties;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "user")
public class UserProperties {

  private List<Double> validPlates = new ArrayList<>();

  private List<Double> validBars = new ArrayList<>();
}
