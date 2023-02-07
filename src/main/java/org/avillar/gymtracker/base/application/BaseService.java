package org.avillar.gymtracker.base.application;

import org.avillar.gymtracker.auth.application.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseService {

    protected ModelMapper modelMapper;
    protected AuthService authService;

    @Autowired
    public final void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public final void setAuthService(AuthService authService) {
        this.authService = authService;
    }

}
