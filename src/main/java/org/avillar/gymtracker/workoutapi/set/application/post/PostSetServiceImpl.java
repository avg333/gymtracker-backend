package org.avillar.gymtracker.workoutapi.set.application.post;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.set.application.post.mapper.PostSetServiceMapper;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetRequestApplication;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.avillar.gymtracker.workoutapi.set.domain.SetDao;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroupDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSetServiceImpl implements PostSetService {

  private final SetDao setDao;
  private final SetGroupDao setGroupDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final PostSetServiceMapper postSetServiceMapper;

  @Override
  public PostSetResponseApplication post(
      final UUID setGroupId, final PostSetRequestApplication postSetRequestApplication) {
    final SetGroup setGroup = getSetGroupFull(setGroupId);

    final Set set = postSetServiceMapper.postRequest(postSetRequestApplication);
    set.setSetGroup(setGroup);
    set.setListOrder(setGroup.getSets().size());

    authWorkoutsService.checkAccess(set, AuthOperations.CREATE);

    setDao.save(set);

    return postSetServiceMapper.postResponse(set);
  }

  private SetGroup getSetGroupFull(final UUID setGroupId) {
    return setGroupDao.getSetGroupFullByIds(List.of(setGroupId)).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId));
  }
}
