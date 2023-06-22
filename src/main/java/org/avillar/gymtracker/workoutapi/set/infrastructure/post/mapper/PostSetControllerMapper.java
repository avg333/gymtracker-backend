package org.avillar.gymtracker.workoutapi.set.infrastructure.post.mapper;

import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetRequestApplication;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.infrastructure.post.model.PostSetRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.set.infrastructure.post.model.PostSetResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostSetControllerMapper {

  PostSetResponseInfrastructure map(PostSetResponseApplication postSetResponseApplication);

  PostSetRequestApplication map(PostSetRequestInfrastructure postSetRequestInfrastructure);
}
