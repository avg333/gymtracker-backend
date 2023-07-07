package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSetGroupListOrderRequest {

  @NotNull private Integer listOrder;
}
