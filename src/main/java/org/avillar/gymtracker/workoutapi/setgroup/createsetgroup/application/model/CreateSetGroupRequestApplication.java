package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model;

import java.util.UUID;
import lombok.Data;

@Data
public class CreateSetGroupRequestApplication {

  private String description;
  private UUID exerciseId;
}
