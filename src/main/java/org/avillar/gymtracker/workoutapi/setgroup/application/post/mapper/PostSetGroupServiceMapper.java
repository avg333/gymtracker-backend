package org.avillar.gymtracker.workoutapi.setgroup.application.post.mapper;

import org.avillar.gymtracker.workoutapi.setgroup.application.post.model.PostSetGroupRequestApplication;
import org.avillar.gymtracker.workoutapi.setgroup.application.post.model.PostSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostSetGroupServiceMapper {

  PostSetGroupResponseApplication postResponse(SetGroup postSetGroupResponse);

  SetGroup postRequest(PostSetGroupRequestApplication postSetGroupRequestApplication);
}
