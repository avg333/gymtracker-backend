package org.avillar.gymtracker.workoutapi.setgroup.application.post.mapper;

import org.avillar.gymtracker.workoutapi.setgroup.application.post.model.PostSetGroupRequest;
import org.avillar.gymtracker.workoutapi.setgroup.application.post.model.PostSetGroupResponse;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostSetGroupServiceMapper {

  PostSetGroupResponse postResponse(SetGroup postSetGroupResponse);

  SetGroup postRequest(PostSetGroupRequest postSetGroupRequest);
}
