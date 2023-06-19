package org.avillar.gymtracker.workoutapi.set.application.post.mapper;

import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetRequestApplication;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostSetServiceMapper {

  PostSetResponseApplication postResponse(Set set);

  Set postRequest(PostSetRequestApplication postSetRequestApplication);
}
