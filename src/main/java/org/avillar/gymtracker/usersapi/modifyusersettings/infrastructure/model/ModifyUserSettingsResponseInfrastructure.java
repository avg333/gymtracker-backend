package org.avillar.gymtracker.usersapi.modifyusersettings.infrastructure.model;

import java.util.List;
import lombok.Data;

@Data
public class ModifyUserSettingsResponseInfrastructure {

  private Boolean internationalSystem;

  private Double selectedIncrement;

  private Double selectedBar;

  private List<Double> selectedPlates;
}
