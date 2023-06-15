package org.avillar.gymtracker.workoutapi.auth.infraestructure;

import org.avillar.gymtracker.common.auth.JwtAuthenticationEntryPoint;
import org.avillar.gymtracker.common.auth.WebSecurityConfigBase;
import org.avillar.gymtracker.common.auth.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
public class WebSecurityConfig extends WebSecurityConfigBase {

  //@Autowired
  public WebSecurityConfig(
      JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtRequestFilter jwtRequestFilter) {
    super(jwtAuthenticationEntryPoint, jwtRequestFilter);
  }
}
