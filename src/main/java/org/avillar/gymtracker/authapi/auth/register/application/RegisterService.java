package org.avillar.gymtracker.authapi.auth.register.application;

import org.avillar.gymtracker.authapi.auth.register.application.model.RegisterRequestApplication;
import org.avillar.gymtracker.authapi.auth.register.application.model.RegisterResponseApplication;

public interface RegisterService {

  RegisterResponseApplication execute(RegisterRequestApplication registerRequestApplication);
}
