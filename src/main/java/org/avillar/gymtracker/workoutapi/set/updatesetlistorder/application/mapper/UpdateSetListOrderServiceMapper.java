package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.model.UpdateSetListOrderResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetListOrderServiceMapper {

  List<UpdateSetListOrderResponseApplication> map(List<Set> sets);
}
