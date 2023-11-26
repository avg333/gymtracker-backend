package org.avillar.gymtracker.workoutapi.common.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import org.avillar.gymtracker.common.auth.AuthenticatedEntity;

@Getter
@Setter
@Builder
public class Workout implements AuthenticatedEntity {

  private UUID id;

  private LocalDate date;

  private String description;

  private UUID userId;

  @Default private List<SetGroup> setGroups = new ArrayList<>();
}
