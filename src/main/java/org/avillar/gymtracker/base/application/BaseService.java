package org.avillar.gymtracker.base.application;

import org.avillar.gymtracker.auth.application.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseService {
    @Autowired
    protected ModelMapper modelMapper;
    @Autowired
    protected AuthService authService;
}