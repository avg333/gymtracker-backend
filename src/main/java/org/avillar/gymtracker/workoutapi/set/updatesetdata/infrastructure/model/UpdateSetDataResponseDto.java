package org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model;

import static org.avillar.gymtracker.workoutapi.common.utils.Constants.ISO_8601_DATE_FORMAT;
import static org.avillar.gymtracker.workoutapi.common.utils.Constants.UTC;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

@Data
public class UpdateSetDataResponseDto {

  private String description;
  private Integer reps;
  private Double rir;
  private Double weight;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ISO_8601_DATE_FORMAT, timezone = UTC)
  private Date completedAt;
}
