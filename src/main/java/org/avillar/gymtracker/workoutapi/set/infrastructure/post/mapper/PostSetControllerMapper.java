package org.avillar.gymtracker.workoutapi.set.infrastructure.post.mapper;

import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetRequestApplication;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.infrastructure.post.model.PostSetRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.set.infrastructure.post.model.PostSetResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostSetControllerMapper {

  PostSetResponseInfrastructure postResponse(PostSetResponseApplication postSetResponseApplication);

  PostSetRequestApplication postRequest(PostSetRequestInfrastructure postSetRequestInfrastructure);
}
