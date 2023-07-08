package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.model;

import java.util.UUID;
import lombok.Data;

@Data
public class GetAllLoadTypesResponse {

  private UUID id;
  private String name;
  private String description;
}
