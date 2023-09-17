package org.avillar.gymtracker.usersapi.getusersettings.application.model;

import java.util.List;
import lombok.Data;

@Data
public class GetUserSettingsResponseApplication {

  private Boolean internationalSystem;

  private Double selectedIncrement;

  private Double selectedBar;

  private List<Double> selectedPlates;
}
