package org.avillar.gymtracker.usersapi.getusersettings.infrastructure.model;

import java.util.List;
import lombok.Data;

@Data
public class GetUserSettingsResponseInfrastructure {

  private Boolean internationalSystem;

  private Double selectedIncrement;

  private Double selectedBar;

  private List<Double> selectedPlates;
}
