package org.avillar.gymtracker.workoutapi.set.application.post.mapper;

import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetRequestApplication;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostSetServiceMapper {

  PostSetResponseApplication map(Set set);

  Set map(PostSetRequestApplication postSetRequestApplication);
}
