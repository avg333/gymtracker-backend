package org.avillar.gymtracker.workoutapi.set.application.post.mapper;

import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetRequest;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetResponse;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostSetServiceMapper {

  PostSetResponse postResponse(Set set);

  Set postRequest(PostSetRequest postSetRequest);
}
