package org.avillar.gymtracker.auth.application;

import org.avillar.gymtracker.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercise.domain.Exercise;
import org.avillar.gymtracker.measure.domain.Measure;
import org.avillar.gymtracker.program.domain.Program;
import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.avillar.gymtracker.user.application.UserAppDto;
import org.avillar.gymtracker.user.domain.UserApp;
import org.avillar.gymtracker.workout.domain.Workout;

public interface AuthService {

    UserAppDto login(UserAppDto userAppDto);

    void logout();

    UserApp getLoggedUser();

    void checkAccess(Exercise exercise) throws IllegalAccessException;

    void checkAccess(Measure measure) throws IllegalAccessException;

    void checkAccess(Program program) throws IllegalAccessException;

    void checkAccess(Workout workout) throws IllegalAccessException;

    void checkAccess(SetGroup setGroup) throws IllegalAccessException;
}
