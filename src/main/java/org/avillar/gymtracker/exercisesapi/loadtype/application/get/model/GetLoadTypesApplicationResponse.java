package org.avillar.gymtracker.exercisesapi.loadtype.application.get.model;

import java.util.UUID;
import lombok.Data;

@Data
public class GetLoadTypesApplicationResponse {

  private UUID id;
  private String name;
  private String description;
}
