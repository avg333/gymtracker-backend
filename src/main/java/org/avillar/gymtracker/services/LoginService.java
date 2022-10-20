package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.entities.Measure;
import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.model.entities.UserApp;

public interface LoginService {

    UserApp getLoggedUser();

    void checkAccess(final Measure measure) throws IllegalAccessException;

    void checkAccess(final Program program) throws IllegalAccessException;
}
