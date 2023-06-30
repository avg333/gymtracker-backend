package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.model;

import java.util.UUID;
import lombok.Data;

@Data
public class GetAllLoadTypesResponseApplication {

  private UUID id;
  private String name;
  private String description;
}
