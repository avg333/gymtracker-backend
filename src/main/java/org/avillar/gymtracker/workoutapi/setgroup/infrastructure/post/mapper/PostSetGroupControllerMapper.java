package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.post.mapper;

import org.avillar.gymtracker.workoutapi.setgroup.application.post.model.PostSetGroupRequestApplication;
import org.avillar.gymtracker.workoutapi.setgroup.application.post.model.PostSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.post.model.PostSetGroupRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.post.model.PostSetGroupResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostSetGroupControllerMapper {

  PostSetGroupResponseInfrastructure map(
      PostSetGroupResponseApplication postSetGroupResponseApplication);

  PostSetGroupRequestApplication map(
      PostSetGroupRequestInfrastructure postSetGroupRequestInfrastructure);
}
