package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.model;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

@Data
public class GetAllLoadTypesResponseApplication implements Serializable {

  private UUID id;

  private String name;

  private String description;
}
