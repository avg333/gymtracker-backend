package org.avillar.gymtracker.workoutapi.set.application.post.model;

import lombok.Data;

@Data
public class PostSetRequest {

  private String description;

  private Integer reps;

  private Double rir;

  private Double weight;
}
