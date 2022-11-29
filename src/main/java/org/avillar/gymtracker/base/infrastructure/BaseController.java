package org.avillar.gymtracker.base.infrastructure;

import org.avillar.gymtracker.auth.application.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class BaseController {
    @Autowired
    protected AuthService authService;
}
