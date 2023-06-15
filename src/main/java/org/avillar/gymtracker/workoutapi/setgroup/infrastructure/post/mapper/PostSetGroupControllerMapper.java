package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.post.mapper;

import org.avillar.gymtracker.workoutapi.setgroup.application.post.model.PostSetGroupResponse;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.post.model.PostSetGroupRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostSetGroupControllerMapper {

  org.avillar.gymtracker.workoutapi.setgroup.infrastructure.post.model.PostSetGroupResponse
      postResponse(PostSetGroupResponse postSetGroupResponse);

  org.avillar.gymtracker.workoutapi.setgroup.application.post.model.PostSetGroupRequest postRequest(
      PostSetGroupRequest postSetGroupRequest);
}
