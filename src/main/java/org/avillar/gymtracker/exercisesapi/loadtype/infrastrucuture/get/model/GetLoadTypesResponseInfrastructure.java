package org.avillar.gymtracker.exercisesapi.loadtype.infrastrucuture.get.model;

import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetLoadTypesResponseInfrastructure {

  private UUID id;
  private String name;
  private String description;
}
