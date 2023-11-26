package org.avillar.gymtracker.workoutapi.common.exception.application;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ListOrderNotValidException extends Exception {

  private final int invalidListOrder;
  private final int minListOrder;
  private final int maxListOrder;

  public ListOrderNotValidException(
      final int invalidListOrder, final int minListOrder, final int maxListOrder) {
    super(
        "The list order: "
            + invalidListOrder
            + " is not valid. It must be between: "
            + minListOrder
            + " and "
            + maxListOrder);
    this.invalidListOrder = invalidListOrder;
    this.minListOrder = minListOrder;
    this.maxListOrder = maxListOrder;
  }
}
