package org.avillar.gymtracker.workoutapi.common.sort.domain;

import java.util.UUID;

public interface SortableEntity {

  UUID getId();

  Integer getListOrder();

  void setListOrder(Integer listOrder);
}
