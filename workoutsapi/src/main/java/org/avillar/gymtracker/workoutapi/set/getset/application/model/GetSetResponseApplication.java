package org.avillar.gymtracker.workoutapi.set.getset.application.model;

import java.util.UUID;
import lombok.Data;

@Data
public class GetSetResponseApplication {

  private UUID id;
  private Integer listOrder;
  private String description;
  private Integer reps;
  private Double rir;
  private Double weight;
  private SetGroup setGroup;

  @Data
  public static class SetGroup {
    private UUID id;
  }
}