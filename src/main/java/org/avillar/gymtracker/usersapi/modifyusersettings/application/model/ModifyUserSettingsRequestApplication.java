package org.avillar.gymtracker.usersapi.modifyusersettings.application.model;

import java.util.List;
import lombok.Data;

@Data
public class ModifyUserSettingsRequestApplication {

  private Boolean internationalSystem;

  private Double selectedIncrement;

  private Double selectedBar;

  private List<Double> selectedPlates;
}
