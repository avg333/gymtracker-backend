package org.avillar.gymtracker.authapi.auth.infrastructure;

import org.avillar.gymtracker.common.auth.JwtAuthenticationEntryPoint;
import org.avillar.gymtracker.common.auth.WebSecurityConfigBase;
import org.avillar.gymtracker.common.auth.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig extends WebSecurityConfigBase {

  private final UserDetailsService userDetailsService;

  @Autowired
  public WebSecurityConfig(
      JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
      JwtRequestFilter jwtRequestFilter,
      UserDetailsService userDetailsService) {
    super(jwtAuthenticationEntryPoint, jwtRequestFilter);
    this.userDetailsService = userDetailsService;
  }

  @Autowired
  public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
