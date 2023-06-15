package org.avillar.gymtracker.common.auth.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

  private static final String AUTHORIZATION_HEADER_START = "Bearer ";

  private final JwtTokenUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal(
      final HttpServletRequest request,
      @NonNull final HttpServletResponse response,
      @NonNull final FilterChain chain)
      throws ServletException, IOException {

    final String requestTokenHeader = request.getHeader("Authorization");
    if (SecurityContextHolder.getContext().getAuthentication() == null
        && StringUtils.isNotEmpty(requestTokenHeader)) {
      if (!StringUtils.startsWith(requestTokenHeader, AUTHORIZATION_HEADER_START)) {
        log.warn("JWT Token does not begin with Bearer String");
      } else {
        verifyJwt(request, requestTokenHeader.substring(7));
      }
    }

    chain.doFilter(request, response);
  }

  private void verifyJwt(final HttpServletRequest request, final String jwtToken) {
    try {
      if (!Boolean.TRUE.equals(jwtTokenUtil.validateToken(jwtToken))) {
        log.info("El token JWT no es válido");
        return;
      }

      final UserDetails userDetails = jwtTokenUtil.getUserDetailsImplFromToken(jwtToken);

      final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
          new UsernamePasswordAuthenticationToken(userDetails, null, null);
      usernamePasswordAuthenticationToken.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    } catch (IllegalArgumentException e) {
      log.error("Unable to fetch JWT Token");
    } catch (ExpiredJwtException e) {
      log.error("JWT Token is expired");
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
