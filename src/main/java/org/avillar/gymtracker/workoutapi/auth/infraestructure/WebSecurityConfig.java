package org.avillar.gymtracker.workoutapi.auth.infraestructure;

import org.avillar.gymtracker.common.auth.JwtAuthenticationEntryPoint;
import org.avillar.gymtracker.common.auth.WebSecurityConfigBase;
import org.avillar.gymtracker.common.auth.jwt.JwtRequestFilter;

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
