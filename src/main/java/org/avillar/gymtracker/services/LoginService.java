package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.entities.*;

public interface LoginService {

    UserApp getLoggedUser();

    void checkAccess(Measure measure) throws IllegalAccessException;

    void checkAccess(Program program) throws IllegalAccessException;

    void checkAccess(Workout workout) throws IllegalAccessException;

    void checkAccess(SetGroup setGroup) throws IllegalAccessException;
}
