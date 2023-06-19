package org.avillar.gymtracker.workoutapi.setgroup.application.post.model;

import java.util.UUID;
import lombok.Data;

@Data
public class PostSetGroupRequestApplication {

  private String description;

  private UUID exerciseId;
}
