package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.entities.Measure;
import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.model.entities.UserApp;
import org.avillar.gymtracker.model.entities.Workout;

public interface LoginService {

    UserApp getLoggedUser();

    void checkAccess(final Measure measure) throws IllegalAccessException;

    void checkAccess(final Program program) throws IllegalAccessException;

    void checkAccess(Workout workout) throws IllegalAccessException;
}
