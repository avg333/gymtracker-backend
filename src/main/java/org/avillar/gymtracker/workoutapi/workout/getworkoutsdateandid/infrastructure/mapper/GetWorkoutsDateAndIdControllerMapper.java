package org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure.mapper;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure.model.GetWorkoutsDateAndIdResponse;
import org.springframework.stereotype.Component;

@Component
public class GetWorkoutsDateAndIdControllerMapper {

  public GetWorkoutsDateAndIdResponse map(final Map<LocalDate, UUID> workoutsDateAndId) {
    if (workoutsDateAndId == null) {
      return null;
    }
    return GetWorkoutsDateAndIdResponse.builder().workoutsDateAndId(workoutsDateAndId).build();
  }
}
