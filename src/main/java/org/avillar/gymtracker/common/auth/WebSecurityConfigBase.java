package org.avillar.gymtracker.common.auth;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.auth.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
public abstract class WebSecurityConfigBase {

  private static final String ACTUATOR_HEALTH_ENDPOINT = "/actuator/health";
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtRequestFilter jwtRequestFilter;

  @Value("${authApiPrefix}")
  private String authApiPrefix;

  @Value("${authApiEndpoint}")
  private String authEndpoint;

  @Value("${authApiRegisterEndpoint}")
  private String authRegisterEndpoint;

  @Bean
  public AuthenticationManager authenticationManager(final AuthenticationConfiguration authConfig)
      throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
    return http.cors(cors -> cors.configurationSource(corsConfiguration()))
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            requests ->
                requests
                    .requestMatchers(
                        new AntPathRequestMatcher(ACTUATOR_HEALTH_ENDPOINT, "GET"),
                        new AntPathRequestMatcher(authApiPrefix + authEndpoint, "POST"),
                        new AntPathRequestMatcher(authApiPrefix + authRegisterEndpoint, "POST"))
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .exceptionHandling(eh -> eh.authenticationEntryPoint(jwtAuthenticationEntryPoint))
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .httpBasic(Customizer.withDefaults())
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  private CorsConfigurationSource corsConfiguration() {
    final CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("*"));
    configuration.setAllowedMethods(List.of("*"));
    configuration.setAllowedHeaders(List.of("*"));
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
