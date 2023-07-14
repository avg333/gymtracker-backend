package org.avillar.gymtracker.authapi.register.application;

import org.avillar.gymtracker.authapi.register.application.model.RegisterRequestApplication;
import org.avillar.gymtracker.authapi.register.application.model.RegisterResponseApplication;

public interface RegisterService {

  RegisterResponseApplication execute(RegisterRequestApplication registerRequestApplication);
}
