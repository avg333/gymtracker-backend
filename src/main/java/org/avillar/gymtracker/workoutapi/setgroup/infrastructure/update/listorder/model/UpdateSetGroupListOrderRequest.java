package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.listorder.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSetGroupListOrderRequest {

  @NotNull private Integer listOrder;
}
