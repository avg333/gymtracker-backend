package org.avillar.gymtracker.exercisesapi.loadtype.infrastrucuture.get.model;

import java.util.UUID;
import lombok.Data;

@Data
public class GetLoadTypesResponseInfrastructure {

  private UUID id;
  private String name;
  private String description;
}
