package org.avillar.gymtracker.workoutapi.set.infrastructure.post.mapper;

import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetRequest;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostSetControllerMapper {

  org.avillar.gymtracker.workoutapi.set.infrastructure.post.model.PostSetResponse postResponse(
      PostSetResponse postSetResponse);

  PostSetRequest postRequest(
      org.avillar.gymtracker.workoutapi.set.infrastructure.post.model.PostSetRequest
          postSetRequest);
}
