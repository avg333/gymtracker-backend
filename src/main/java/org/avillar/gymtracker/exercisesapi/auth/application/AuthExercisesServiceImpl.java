package org.avillar.gymtracker.exercisesapi.auth.application;

import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.domain.Exercise;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthExercisesServiceImpl implements AuthExercisesService {

  @Override
  public void checkAccess(final Exercise exercise, final AuthOperations authOperations)
      throws IllegalAccessException {
    final UUID userId = getLoggedUserId();

    if (userId == null) {
      throw new IllegalAccessException(exercise, authOperations, userId);
    }
  }

  @Override
  public void checkAccess(final List<Exercise> exercises, final AuthOperations authOperations) {
    exercises.forEach(exercise -> this.checkAccess(exercise, authOperations));
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
