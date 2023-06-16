package org.avillar.gymtracker.exercisesapi.loadtype.application.get.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetLoadTypeResponse {

  private UUID id;
  private String name;
  private String description;
}
