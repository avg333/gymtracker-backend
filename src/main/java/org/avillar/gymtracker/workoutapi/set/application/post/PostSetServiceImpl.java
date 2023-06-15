package org.avillar.gymtracker.workoutapi.set.application.post;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.set.application.post.mapper.PostSetServiceMapper;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetRequest;
import org.avillar.gymtracker.workoutapi.set.application.post.model.PostSetResponse;
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
  public PostSetResponse post(final UUID setGroupId, final PostSetRequest postSetRequest) {
    final SetGroup setGroup = getSetGroupFull(setGroupId);

    final Set set = postSetServiceMapper.postRequest(postSetRequest);
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
