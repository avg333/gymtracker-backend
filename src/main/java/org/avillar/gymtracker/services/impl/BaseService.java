package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.services.LoginService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseService {
    @Autowired
    protected ModelMapper modelMapper;
    @Autowired
    protected LoginService loginService;
}