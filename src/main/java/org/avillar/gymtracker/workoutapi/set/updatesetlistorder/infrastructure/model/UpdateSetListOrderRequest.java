package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSetListOrderRequest {

  @NotNull private Integer listOrder;
}
