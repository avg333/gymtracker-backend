package org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model;

import java.util.Date;
import lombok.Data;

@Data
public class UpdateSetDataResponseApplication {

  private String description;
  private Integer reps;
  private Double rir;
  private Double weight;
  private Date completedAt;
}
