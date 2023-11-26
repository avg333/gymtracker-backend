package org.avillar.gymtracker.exercisesapi.common.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoadType {

  private UUID id;

  private String name;

  private String description;
}
