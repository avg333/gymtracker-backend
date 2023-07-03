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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
public class WebSecurityConfigBase {

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtRequestFilter jwtRequestFilter;

  @Value("${authApiPrefix}")
  private String authApiPrefix;

  @Value("${authApiEndpoint}")
  private String authEndpoint;

  @Bean
  public AuthenticationManager authenticationManager(final AuthenticationConfiguration authConfig)
      throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
    return http.cors(cors -> cors.configurationSource(corsConfiguration()))
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests( // FIXME Autorizar solo API
            authorize -> authorize.requestMatchers("/**").permitAll().anyRequest().authenticated())
        .exceptionHandling(eh -> eh.authenticationEntryPoint(jwtAuthenticationEntryPoint))
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .httpBasic(Customizer.withDefaults())
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  //        .authorizeHttpRequests(
  //            requests ->
  //                requests
  //                    .requestMatchers(new AntPathRequestMatcher(authApiPrefix +
  // authEndpoint))
  //                    .permitAll()
  //                    .anyRequest()
  //                    .authenticated())
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
