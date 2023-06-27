package org.avillar.gymtracker.workoutapi.auth.application;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthWorkoutsServiceImpl implements AuthWorkoutsService {

  @Override
  public void checkAccess(final Workout workout, final AuthOperations authOperations)
      throws IllegalAccessException {
    final UUID userId = getLoggedUserId();

    if (null != workout && !userId.equals(workout.getUserId())) {
      throw new IllegalAccessException(workout, authOperations, userId);
    }
  }

  @Override
  public void checkAccess(final SetGroup setGroup, final AuthOperations authOperations)
      throws IllegalAccessException {
    if (null != setGroup.getWorkout()) {
      checkAccess(setGroup.getWorkout(), authOperations);
    }
  }

  @Override
  public void checkAccess(final Set set, final AuthOperations authOperations)
      throws IllegalAccessException {
    if (null != set.getSetGroup()) {
      checkAccess(set.getSetGroup(), authOperations);
    }
  }

  private UUID getLoggedUserId() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      throw new RuntimeException("No user logged in!!");
    }

    final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    return userDetails.getId();
  }
}
