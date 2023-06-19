package org.avillar.gymtracker.workoutapi.set.infrastructure.update.listorder.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSetListOrderRequestInfrastructure {

  @NotNull private Integer listOrder;
}
