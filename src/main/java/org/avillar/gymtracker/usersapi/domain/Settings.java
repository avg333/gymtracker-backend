package org.avillar.gymtracker.usersapi.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.common.base.domain.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Settings extends BaseEntity {

  private UUID userId;

  private boolean internationalSystem;

  private double selectedIncrement;

  private double selectedBar;

  @ElementCollection(fetch = FetchType.EAGER)
  private List<Double> selectedPlates = new ArrayList<>();
}
