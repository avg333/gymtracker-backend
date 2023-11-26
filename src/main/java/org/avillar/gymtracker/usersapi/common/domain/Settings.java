package org.avillar.gymtracker.usersapi.common.domain;

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
public class Settings implements AuthenticatedEntity {

  private UUID id;

  private UUID userId;

  private Boolean internationalSystem;

  private Double selectedIncrement;

  private Double selectedBar;

  @Default private List<Double> selectedPlates = new ArrayList<>();
}
